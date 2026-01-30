package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import com.oliinyk.yaroslav.easyreads.domain.service.ReadTimeCounterService
import com.oliinyk.yaroslav.easyreads.domain.util.BookUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReadingSessionRecordViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
        private val noteRepository: NoteRepository,
        private val readingSessionRepository: ReadingSessionRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ReadingSessionRecordUiState())
        val uiState
            get() = _uiState.asStateFlow()

        val currentReadingSession
            get() = uiState.value.readingSession

        fun setup(bookId: UUID) {
            loadBookById(bookId)
            loadLastUnfinishedByBookId(bookId)
            loadNoteCount(bookId)
        }

        private fun loadBookById(bookId: UUID) {
            viewModelScope.launch {
                bookRepository.getById(bookId).collect { book ->
                    book?.let {
                        _uiState.update { it.copy(book = book) }
                    }
                }
            }
        }

        private fun loadLastUnfinishedByBookId(bookId: UUID) {
            viewModelScope.launch {
                readingSessionRepository.getLastUnfinishedByBookId(bookId).collect { readingSessionFromDB ->
                    readingSessionFromDB?.let {
                        _uiState.update { it.copy(readingSession = readingSessionFromDB) }
                    }
                }
            }
        }

        private fun loadNoteCount(bookId: UUID) {
            viewModelScope.launch {
                noteRepository.getAllByBookId(bookId).collect { notes ->
                    _uiState.update { it.copy(notesCount = notes.size) }
                }
            }
        }

        fun updateStateUi(onUpdate: (ReadingSessionRecordUiState) -> ReadingSessionRecordUiState) {
            _uiState.update { onUpdate(it) }
        }

        fun resumeOrPause(onUpdate: (ReadTimeCounterService.Actions) -> Unit) {
            _uiState.value.readingSession?.let { readingSession ->
                when (readingSession.recordStatus) {
                    ReadingSessionRecordStatusType.PAUSED -> {
                        onUpdate(ReadTimeCounterService.Actions.RESUME)
                    }

                    ReadingSessionRecordStatusType.STARTED -> {
                        onUpdate(ReadTimeCounterService.Actions.PAUSE)
                    }

                    else -> {
                        onUpdate(ReadTimeCounterService.Actions.PAUSE)
                    }
                }
            }
        }

        fun removeUnfinishedReadingSession() {
            _uiState.value.readingSession?.let { readingSession ->
                readingSessionRepository.remove(readingSession)
            }
            updateStateUi { it.copy(readingSession = null) }
        }

        fun save(readingSession: ReadingSession) {
            updateBook(readingSession)
            readingSessionRepository.update(
                readingSession.copy(recordStatus = ReadingSessionRecordStatusType.FINISHED),
            )
            updateStateUi { it.copy(readingSession = null) }
        }

        private fun updateBook(readingSession: ReadingSession) {
            uiState.value.book?.let { book ->
                val (isFinished: Boolean, finishedAt: LocalDateTime?, shelf: BookShelvesType) =
                    BookUtil.getFinishedWithFinishedAtAndShelf(book, readingSession)
                bookRepository.update(
                    book.copy(
                        pageCurrent = readingSession.endPage,
                        updatedAt = readingSession.startedAt,
                        isFinished = isFinished,
                        finishedAt = finishedAt,
                        shelf = shelf,
                    ),
                )
            }
        }

        fun addNote(note: Note) {
            noteRepository.save(note)
        }
    }

data class ReadingSessionRecordUiState(
    val book: Book? = null,
    val readingSession: ReadingSession? = null,
    val notesCount: Int = 0,
)

sealed interface ReadingSessionRecordEvent {
    object OnStartPause : ReadingSessionRecordEvent

    object OnPause : ReadingSessionRecordEvent

    data class OnFinish(
        val readingSession: ReadingSession,
    ) : ReadingSessionRecordEvent

    object OnShowNotes : ReadingSessionRecordEvent

    data class OnAddNote(
        val note: Note,
    ) : ReadingSessionRecordEvent
}
