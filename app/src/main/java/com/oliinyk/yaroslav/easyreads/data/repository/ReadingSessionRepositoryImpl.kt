package com.oliinyk.yaroslav.easyreads.data.repository

import com.oliinyk.yaroslav.easyreads.data.local.dao.ReadingSessionDao
import com.oliinyk.yaroslav.easyreads.data.local.entety.toModel
import com.oliinyk.yaroslav.easyreads.di.AppCoroutineScope
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.model.toEntity
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadingSessionRepositoryImpl
    @Inject
    constructor(
        private val readingSessionDao: ReadingSessionDao,
        @AppCoroutineScope private val coroutineScope: CoroutineScope,
        @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    ) : ReadingSessionRepository {
        override suspend fun getAll(): List<ReadingSession> =
            readingSessionDao.getAll().map {
                it.toModel()
            }

        override fun getAllByBookId(bookId: UUID): Flow<List<ReadingSession>> =
            readingSessionDao
                .getAllByBookId(bookId)
                .map { entities ->
                    entities.map {
                        it.toModel()
                    }
                }.distinctUntilChanged()

        override suspend fun getAllByBookIds(bookIds: List<UUID>): List<ReadingSession> =
            readingSessionDao.getAllByBookIds(bookIds).map {
                it.toModel()
            }

        override fun getLastUnfinishedByBookId(bookId: UUID): Flow<ReadingSession?> =
            readingSessionDao
                .getLastUnfinishedByBookId(bookId)
                .map {
                    it?.toModel()
                }.distinctUntilChanged()

        override fun save(readingSession: ReadingSession) {
            coroutineScope.launch(ioDispatcher) {
                readingSessionDao.insert(readingSession.toEntity())
            }
        }

        override fun saveAll(readingSessions: List<ReadingSession>) {
            coroutineScope.launch(ioDispatcher) {
                readingSessionDao.upsertAll(readingSessions.map { it.toEntity() })
            }
        }

        override fun update(readingSession: ReadingSession) {
            coroutineScope.launch(ioDispatcher) {
                readingSessionDao.update(readingSession.toEntity())
            }
        }

        override fun remove(readingSession: ReadingSession) {
            coroutineScope.launch(ioDispatcher) {
                readingSessionDao.delete(readingSession.toEntity())
            }
        }

        override fun remove(readingSessions: List<ReadingSession>) {
            coroutineScope.launch(ioDispatcher) {
                readingSessionDao.delete(
                    readingSessions.map { it.toEntity() },
                )
            }
        }
    }
