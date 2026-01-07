package com.oliinyk.yaroslav.easyreads.domain.repository

import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ReadingSessionRepository {
    suspend fun getAll(): List<ReadingSession>

    fun getAllByBookId(bookId: UUID): Flow<List<ReadingSession>>

    suspend fun getAllByBookIds(bookIds: List<UUID>): List<ReadingSession>

    fun getLastUnfinishedByBookId(bookId: UUID): Flow<ReadingSession?>

    fun save(readingSession: ReadingSession)

    fun saveAll(readingSessions: List<ReadingSession>)

    fun update(readingSession: ReadingSession)

    fun remove(readingSession: ReadingSession)

    fun remove(readingSessions: List<ReadingSession>)
}
