package com.oliinyk.yaroslav.easyreads.data.repository

import androidx.room.withTransaction
import com.oliinyk.yaroslav.easyreads.data.local.AppDatabase
import com.oliinyk.yaroslav.easyreads.domain.model.BackupData
import com.oliinyk.yaroslav.easyreads.domain.repository.BackupRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingGoalRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl
    @Inject
    constructor(
        private val database: AppDatabase,
        private val bookRepository: BookRepository,
        private val noteRepository: NoteRepository,
        private val readingSessionRepository: ReadingSessionRepository,
        private val readingGoalRepository: ReadingGoalRepository,
    ) : BackupRepository {
        override suspend fun create(): BackupData =
            database.withTransaction {
                val books = bookRepository.getAll()
                val notes = noteRepository.getAll()
                val readingSessions = readingSessionRepository.getAll()
                val readingGoals = readingGoalRepository.getAll()

                BackupData(
                    version = AppConstants.DATABASE_VERSION,
                    exportedAtEpochMillis =
                        LocalDateTime
                            .now()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli(),
                    books = books,
                    notes = notes,
                    readingSessions = readingSessions,
                    readingGoals = readingGoals,
                )
            }

        override suspend fun save(backupData: BackupData) {
            database.withTransaction {
                bookRepository.saveAll(backupData.books)
                noteRepository.saveAll(backupData.notes)
                readingSessionRepository.saveAll(backupData.readingSessions)
                readingGoalRepository.saveAll(backupData.readingGoals)
            }
        }
    }
