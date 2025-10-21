package com.oliinyk.yaroslav.easyreads.presentation.note.list

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
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _stateUi: MutableStateFlow<NoteListStateUi> = MutableStateFlow(NoteListStateUi())
    val stateUi: StateFlow<NoteListStateUi>
        get() = _stateUi.asStateFlow()

    private val _editingNote: MutableStateFlow<Note?> = MutableStateFlow(null)
    val editingNote: StateFlow<Note?>
        get() = _editingNote.asStateFlow()

    private val _removingNote: MutableStateFlow<Note?> = MutableStateFlow(null)
    val removingNote: StateFlow<Note?>
        get() = _removingNote.asStateFlow()

    fun loadNotes(bookId: UUID) {
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
            noteRepository.insert(note.copy(bookId = bookId))
        }
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun remove(note: Note) {
        noteRepository.remove(note)
    }

    fun openAddDialog() {
        _editingNote.value = Note()
    }

    fun openEditDialog(note: Note) {
        _editingNote.value = note
    }

    fun dismissAddEditDialog() {
        _editingNote.value = null
    }

    fun openRemoveDialog(note: Note) {
        _removingNote.value = note
    }

    fun dismissRemoveDialog() {
        _removingNote.value = null
    }
}

data class NoteListStateUi(
    val bookId: UUID? = null,
    val notes: List<Note> = emptyList()
)