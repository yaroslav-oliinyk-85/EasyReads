package com.oliinyk.yaroslav.easyreads.domain.usecase

import android.content.Context
import android.net.Uri
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException.NotFoundBackupFileImportException
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException.UnsupportedVersionImportException
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException.ValidationBackupImportException
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException.WriteToFileImportException
import com.oliinyk.yaroslav.easyreads.domain.model.BackupData
import com.oliinyk.yaroslav.easyreads.domain.repository.BackupRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream
import javax.inject.Inject

class ImportBackupDataUseCase
    @Inject
    constructor(
        private val backupRepository: BackupRepository,
        private val json: Json,
        @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    ) {
        suspend operator fun invoke(
            context: Context,
            uri: Uri,
        ) {
            withContext(ioDispatcher) {
                val applicationContext = context.applicationContext
                val contentResolver = context.applicationContext.contentResolver

                contentResolver
                    .openInputStream(uri)
                    ?.use { inputStream ->
                        ZipInputStream(inputStream).use { zipInputStream ->
                            while (true) {
                                val entry = zipInputStream.nextEntry ?: break

                                if (entry.name == AppConstants.BACKUP_FILE_NAME) {
                                    // "backup-data.json"
                                    val byteArrayOutputStream = ByteArrayOutputStream()
                                    zipInputStream.copyTo(byteArrayOutputStream)

                                    val backupDataJsonString: String? =
                                        byteArrayOutputStream.toString(Charsets.UTF_8.name())

                                    backupDataJsonString?.let {
                                        val backupData = json.decodeFromString<BackupData>(it)
                                        validate(backupData)
                                        backupRepository.save(backupData)
                                    } ?: throw NotFoundBackupFileImportException(
                                        "${AppConstants.BACKUP_FILE_NAME} not found in ZIP",
                                    )
                                } else {
                                    // book cover images
                                    val imageFile = File(applicationContext.filesDir, entry.name)
                                    FileOutputStream(imageFile).use { fileOutputStream ->
                                        zipInputStream.copyTo(fileOutputStream)
                                    }
                                }
                                zipInputStream.closeEntry()
                            }
                        }
                    } ?: throw WriteToFileImportException("Cannot open input stream for $uri")
            }
        }

        private fun validate(backupData: BackupData) {
            if (backupData.version != AppConstants.DATABASE_VERSION) {
                throw UnsupportedVersionImportException(
                    "Unsupported backup version: ${backupData.version}. " +
                        "Current version: ${AppConstants.DATABASE_VERSION}",
                )
            }

            val bookIds = backupData.books.map { it.id }.toSet()

            val invalidSessions = backupData.readingSessions.filter { it.bookId !in bookIds }
            val invalidNotes = backupData.notes.filter { it.bookId !in bookIds }

            if (invalidSessions.isNotEmpty() || invalidNotes.isNotEmpty()) {
                throw ValidationBackupImportException(
                    "Backup is invalid: " +
                        "sessions without book=${invalidSessions.size}, " +
                        "notes without book=${invalidNotes.size}",
                )
            }
        }
    }
