package com.oliinyk.yaroslav.easyreads.presentation.book.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MILLISECONDS_IN_ONE_SECOND
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MINUTES_IN_ONE_HOUR
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.SECONDS_IN_ONE_MINUTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val noteRepository: NoteRepository,
    private val readingSessionRepository: ReadingSessionRepository
) : ViewModel() {

    private val _stateUi: MutableStateFlow<BookDetailsStateUi> =
        MutableStateFlow(BookDetailsStateUi())
    val stateUi: StateFlow<BookDetailsStateUi>
        get() = _stateUi.asStateFlow()

    fun loadBookById(bookId: UUID) {
        viewModelScope.launch {
            bookRepository.getById(bookId).collect { bookCollected ->
                bookCollected?.let { book ->
                    _stateUi.update { oldUiState ->
                        oldUiState.copy(book = book)
                    }
                }
            }
        }
        viewModelScope.launch {
            noteRepository.getAllByBookId(bookId).collect { notes ->
                _stateUi.update { it.copy(notes = notes) }
            }
        }
        viewModelScope.launch {
            readingSessionRepository.getAllByBookId(bookId).collect { readingSessions ->
                _stateUi.update { it.copy(readingSessions = readingSessions) }
            }
        }
    }

    fun getCurrentBook(): Book = stateUi.value.book

    fun removeCurrentBook() {
        bookRepository.remove(stateUi.value.book)
        noteRepository.remove(stateUi.value.notes)
        readingSessionRepository.remove(stateUi.value.readingSessions)
    }

    fun updateStateUi(onUpdate: (BookDetailsStateUi) -> BookDetailsStateUi) {
        _stateUi.update {
            onUpdate(it)
        }
    }

    fun getNotes(): List<Note> {
        return stateUi.value.notes
    }

    fun addNote(note: Note) {
        noteRepository.insert(
            note.copy(bookId = stateUi.value.book.id)
        )
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

    fun getReadingSessions(): List<ReadingSession> {
        return stateUi.value.readingSessions
    }

    fun addReadingSession(readingSession: ReadingSession) {
        bookRepository.update(
            stateUi.value.book.copy(pageCurrent = readingSession.endPage)
        )

        readingSessionRepository.insert(
            readingSession.copy(
                bookId = stateUi.value.book.id
            )
        )
    }

    fun updateReadingSession(readingSession: ReadingSession) {
        bookRepository.update(
            stateUi.value.book.copy(pageCurrent = readingSession.endPage)
        )

        readingSessionRepository.update(readingSession)
    }

    fun handleEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.AddNote -> {
                addNote(event.note.copy(bookId = stateUi.value.book.id))
            }
            is BookDetailsEvent.EditNote -> {
                updateNote(event.note)
            }
            else -> { /* TODO: */  }
        }
    }
}

data class BookDetailsStateUi(
    val book: Book = Book(),
    val notes: List<Note> = emptyList(),
    val readingSessions: List<ReadingSession> = emptyList()
) {
    val percentage: Int
        get() {
            return if (book.pageAmount != 0) {
                book.pageCurrent * 100 / book.pageAmount
            } else {
                0
            }
        }
    private val totalReadMinutes: Int
        get() {
            return if (readingSessions.isNotEmpty()) {
                val totalReadTimeInSeconds = readingSessions
                    .map { (it.readTimeInMilliseconds / MILLISECONDS_IN_ONE_SECOND).toInt() }
                    .reduce { acc, i -> acc + i }
                totalReadTimeInSeconds / SECONDS_IN_ONE_MINUTE
            } else {
                0
            }
        }
    val readHours: Int
        get() {
            return if (totalReadMinutes != 0) totalReadMinutes / MINUTES_IN_ONE_HOUR else 0
        }
    val readMinutes: Int
        get() {
            return if (totalReadMinutes != 0)  totalReadMinutes % MINUTES_IN_ONE_HOUR else 0
        }
    val readPagesHour: Int
        get() {
            return if (readingSessions.isNotEmpty()) {
                readingSessions
                    .map { it.readPagesHour }
                    .reduce { acc, i -> acc + i } / readingSessions.size
            } else {
                0
            }
        }
}

sealed interface BookDetailsEvent {
    object SeeAllNotes : BookDetailsEvent
    data class AddNote(val note: Note) : BookDetailsEvent
    data class EditNote(val note: Note) : BookDetailsEvent
    object StartReadingSession : BookDetailsEvent
    object SeeAllReadingSessions : BookDetailsEvent
    object AddReadingSession : BookDetailsEvent
    data class EditReadingSession(val readingSession: ReadingSession) : BookDetailsEvent
}
