package com.oliinyk.yaroslav.easyreads.ui.screen.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val bookRepository: BookRepository,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState())
        val uiState: StateFlow<NoteListUiState>
            get() = _uiState.asStateFlow()

        fun setup(bookId: UUID) {
            getPagesCount(bookId)
            loadNotes(bookId)
        }

        private fun getPagesCount(bookId: UUID) {
            viewModelScope.launch {
                val book = bookRepository.getById(bookId).first()
                _uiState.update { it.copy(pagesCount = book?.pagesCount ?: 0) }
            }
        }

        private fun loadNotes(bookId: UUID) {
            _uiState.update { it.copy(bookId = bookId) }
            viewModelScope.launch {
                noteRepository.getAllByBookId(bookId).collect { notes ->
                    _uiState.update { it.copy(notes = notes) }
                }
            }
        }

        fun save(note: Note) {
            if (note.bookId == null) {
                add(note)
            } else {
                update(note)
            }
        }

        fun add(note: Note) {
            uiState.value.bookId?.let { bookId ->
                noteRepository.save(note.copy(bookId = bookId))
            }
        }

        fun update(note: Note) {
            noteRepository.update(note)
        }

        fun remove(note: Note) {
            note.bookId?.run {
                noteRepository.remove(note)
            }
        }
    }

data class NoteListUiState(
    val bookId: UUID? = null,
    val pagesCount: Int = 0,
    val notes: List<Note> = emptyList(),
)
