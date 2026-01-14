package com.oliinyk.yaroslav.easyreads.ui.screen.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) : ViewModel() {
        private val _stateUi: MutableStateFlow<NoteListStateUi> = MutableStateFlow(NoteListStateUi())
        val stateUi: StateFlow<NoteListStateUi>
            get() = _stateUi.asStateFlow()

        fun setup(bookId: UUID) {
            loadNotes(bookId)
        }

        private fun loadNotes(bookId: UUID) {
            _stateUi.update { it.copy(bookId = bookId) }
            viewModelScope.launch {
                noteRepository.getAllByBookId(bookId).collect { notes ->
                    _stateUi.update { it.copy(notes = notes) }
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
            stateUi.value.bookId?.let { bookId ->
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

data class NoteListStateUi(
    val bookId: UUID? = null,
    val notes: List<Note> = emptyList(),
)
