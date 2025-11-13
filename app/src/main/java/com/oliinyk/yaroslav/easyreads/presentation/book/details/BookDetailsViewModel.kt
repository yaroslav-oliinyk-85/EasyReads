package com.oliinyk.yaroslav.easyreads.presentation.book.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
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
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val noteRepository: NoteRepository,
    private val readingSessionRepository: ReadingSessionRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<BookDetailsUiState> =
        MutableStateFlow(BookDetailsUiState())
    val uiState: StateFlow<BookDetailsUiState>
        get() = _uiState.asStateFlow()

    fun setup(bookId: UUID) {
        loadBookById(bookId)
        loadNotesByBookId(bookId)
        loadReadingSessionsByBookId(bookId)
    }

    private fun loadBookById(bookId: UUID) {
        viewModelScope.launch {
            bookRepository.getById(bookId).collect { bookCollected ->
                bookCollected?.let { book ->
                    _uiState.update { oldUiState ->
                        oldUiState.copy(book = book)
                    }
                }
            }
        }
    }

    private fun loadNotesByBookId(bookId: UUID) {
        viewModelScope.launch {
            noteRepository.getAllByBookId(bookId).collect { notes ->
                _uiState.update { it.copy(notes = notes) }
            }
        }
    }

    private fun loadReadingSessionsByBookId(bookId: UUID) {
        viewModelScope.launch {
            readingSessionRepository.getAllByBookId(bookId).collect { readingSessions ->
                _uiState.update { it.copy(readingSessions = readingSessions) }
            }
        }
    }

    fun removeCurrentBook() {
        bookRepository.remove(uiState.value.book)
        noteRepository.remove(uiState.value.notes)
        readingSessionRepository.remove(uiState.value.readingSessions)
    }

    fun addNote(note: Note) {
        noteRepository.insert(
            note.copy(bookId = uiState.value.book.id)
        )
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

    fun addReadingSession(readingSession: ReadingSession) {
        bookRepository.update(
            uiState.value.book.copy(pageCurrent = readingSession.endPage)
        )

        readingSessionRepository.insert(
            readingSession.copy(
                bookId = uiState.value.book.id
            )
        )
    }

    fun updateReadingSession(readingSession: ReadingSession) {
        bookRepository.update(
            uiState.value.book.copy(pageCurrent = readingSession.endPage)
        )

        readingSessionRepository.update(readingSession)
    }

    fun handleEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.EditReadingSession -> {
                if (event.readingSession.bookId == null) {
                    addReadingSession(event.readingSession)
                } else {
                    updateReadingSession(event.readingSession)
                }
            }
            is BookDetailsEvent.AddNote -> {
                addNote(event.note.copy(bookId = uiState.value.book.id))
            }
            is BookDetailsEvent.EditNote -> {
                updateNote(event.note)
            }
            is BookDetailsEvent.ShelfChanged -> {
                if (_uiState.value.book.shelf != event.shelf) {
                    var bookChanged = _uiState.value.book.copy(shelf = event.shelf)

                    if (!bookChanged.isFinished && bookChanged.shelf == BookShelvesType.FINISHED) {
                        bookChanged = bookChanged.copy(isFinished = true, finishedDate = Date())
                    } else if (bookChanged.isFinished && bookChanged.shelf != BookShelvesType.FINISHED) {
                        bookChanged = bookChanged.copy(isFinished = false, finishedDate = null)
                    }
                    bookRepository.update(book = bookChanged)
                }
            }
        }
    }
}

data class BookDetailsUiState(
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
    data class ShelfChanged(val shelf: BookShelvesType) : BookDetailsEvent
    data class AddNote(val note: Note) : BookDetailsEvent
    data class EditNote(val note: Note) : BookDetailsEvent
    data class EditReadingSession(val readingSession: ReadingSession) : BookDetailsEvent
}
