package com.oliinyk.yaroslav.easyreads.data.repository

import com.oliinyk.yaroslav.easyreads.di.AppCoroutineScope
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadTimeCounterRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadTimeCounterRepositoryImpl
    @Inject
    constructor(
        private val readingSessionRepository: ReadingSessionRepository,
        @AppCoroutineScope private val coroutineScope: CoroutineScope,
        @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    ) : ReadTimeCounterRepository {
        private var timeCounterJob: Job? = null
        private var _readingSession: ReadingSession = ReadingSession()

        private var onTick: (LocalDateTime) -> Unit = { dateTime ->
            if (timeCounterJob?.isActive == true) {
                val timeDifference = Duration.between(_readingSession.updatedAt, dateTime).toMillis()
                _readingSession =
                    _readingSession.copy(
                        updatedAt = dateTime,
                        readTimeInMilliseconds = _readingSession.readTimeInMilliseconds + timeDifference,
                    )
                readingSessionRepository.update(_readingSession)
            }
        }

        private fun updateReadingSession(readingSession: ReadingSession) {
            timeCounterJob?.cancel()

            _readingSession =
                if (_readingSession.recordStatus == ReadingSessionRecordStatusType.STARTED) {
                    readingSession.copy()
                } else {
                    readingSession.copy(
                        updatedAt = LocalDateTime.now(),
                    )
                }
        }

        override fun getReadingSession(): ReadingSession = _readingSession.copy()

        override fun start(
            bookId: UUID,
            pageCurrent: Int,
        ) {
            timeCounterJob?.cancel()

            coroutineScope.launch(ioDispatcher) {
                val readingSession =
                    readingSessionRepository
                        .getLastUnfinishedByBookId(bookId)
                        .firstOrNull()

                if (readingSession == null) {
                    _readingSession =
                        ReadingSession(
                            bookId = bookId,
                            startPage = pageCurrent,
                        )
                    readingSessionRepository.insert(_readingSession)
                } else {
                    updateReadingSession(readingSession)
                }
            }

            timeCounterJob =
                coroutineScope.launch(ioDispatcher) {
                    createTimeCounter().collect { date ->
                        onTick(date)
                    }
                }
        }

        override fun resume() {
            timeCounterJob?.cancel()

            _readingSession =
                _readingSession.copy(
                    updatedAt = LocalDateTime.now(),
                    recordStatus = ReadingSessionRecordStatusType.STARTED,
                )
            readingSessionRepository.update(_readingSession)

            timeCounterJob =
                coroutineScope.launch(ioDispatcher) {
                    createTimeCounter().collect { date ->
                        onTick(date)
                    }
                }
        }

        override fun pause() {
            timeCounterJob?.cancel()

            _readingSession =
                _readingSession.copy(
                    recordStatus = ReadingSessionRecordStatusType.PAUSED,
                )
            readingSessionRepository.update(_readingSession)
        }

        override fun stop() {
            timeCounterJob?.cancel()
            timeCounterJob = null
            _readingSession = ReadingSession()
        }

        private fun createTimeCounter(): Flow<LocalDateTime> =
            flow {
                while (true) {
                    delay(AppConstants.MILLISECONDS_IN_ONE_SECOND)
                    emit(LocalDateTime.now())
                }
            }
    }
