package com.oliinyk.yaroslav.easyreads.domain.usecase

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException.WriteToFileExportException
import com.oliinyk.yaroslav.easyreads.domain.repository.BackupRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject

class ExportBackupDataUseCase
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

                val backupData = backupRepository.create()

                val backupDataJsonString = json.encodeToString(backupData)

                contentResolver
                    .openOutputStream(uri)
                    ?.use { outputStream ->
                        ZipOutputStream(outputStream).use { zipOutputStream ->
                            // Add "backup-data.json"
                            zipOutputStream.putNextEntry(ZipEntry(AppConstants.BACKUP_FILE_NAME))
                            zipOutputStream.write(backupDataJsonString.toByteArray(Charsets.UTF_8))
                            zipOutputStream.closeEntry()

                            // Add book cover images
                            for (book in backupData.books) {
                                val coverImageFileName = book.coverImageFileName
                                coverImageFileName?.let { fileName ->
                                    val imageFile = File(applicationContext.filesDir, fileName)
                                    if (imageFile.exists()) {
                                        contentResolver.openInputStream(imageFile.toUri())?.use { inputStream ->
                                            zipOutputStream.putNextEntry(ZipEntry(coverImageFileName))
                                            inputStream.copyTo(zipOutputStream)
                                            zipOutputStream.closeEntry()
                                        }
                                    }
                                }
                            }
                        }
                    } ?: throw WriteToFileExportException(errorMessage = "Cannot open output stream for $uri")
            }
        }
    }
