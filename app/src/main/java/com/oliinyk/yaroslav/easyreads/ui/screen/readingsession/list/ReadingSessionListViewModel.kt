package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReadingSessionListViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
        private val readingSessionRepository: ReadingSessionRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ReadingSessionListUiState())
        val uiState
            get() = _uiState.asStateFlow()

        fun setup(bookId: UUID) {
            loadReadingSessionsByBookId(bookId)
        }

        private fun loadReadingSessionsByBookId(bookId: UUID) {
            viewModelScope.launch {
                bookRepository
                    .getById(bookId)
                    .combine(readingSessionRepository.getAllByBookId(bookId = bookId)) { book, readingSessions ->
                        ReadingSessionListUiState(
                            book = book,
                            readingSessions = readingSessions,
                        )
                    }.collect { uiStateUpdated ->
                        _uiState.update {
                            it.copy(
                                book = uiStateUpdated.book,
                                readingSessions = uiStateUpdated.readingSessions,
                            )
                        }
                    }
            }
        }

        private fun addReadingSession(readingSession: ReadingSession) {
            uiState.value.book?.let { book ->
                bookRepository.update(
                    book.copy(
                        pageCurrent = readingSession.endPage,
                        updatedAt = LocalDateTime.now(),
                    ),
                )

                readingSessionRepository.insert(
                    readingSession.copy(
                        bookId = book.id,
                    ),
                )
            }
        }

        private fun updateReadingSession(readingSession: ReadingSession) {
            uiState.value.book?.let { book ->

                if (_uiState.value.readingSessions[0].id == readingSession.id) {
                    bookRepository.update(
                        book.copy(pageCurrent = readingSession.endPage),
                    )
                }

                readingSessionRepository.update(readingSession)
            }
        }

        fun save(readingSession: ReadingSession) {
            if (readingSession.bookId == null) {
                addReadingSession(readingSession)
            } else {
                updateReadingSession(readingSession)
            }
        }

        fun remove(readingSession: ReadingSession) {
            uiState.value.book?.let { book ->

                if (book.pageCurrent == readingSession.endPage) {
                    bookRepository.update(
                        book.copy(pageCurrent = readingSession.startPage),
                    )
                }

                readingSessionRepository.remove(readingSession)
            }
        }
    }

data class ReadingSessionListUiState(
    val book: Book? = null,
    val readingSessions: List<ReadingSession> = emptyList(),
)
