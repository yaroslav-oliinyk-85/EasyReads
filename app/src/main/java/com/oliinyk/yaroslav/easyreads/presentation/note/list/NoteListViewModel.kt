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

    fun loadNotes(bookId: UUID) {
        viewModelScope.launch {
            noteRepository.getAllByBookId(bookId).collect { notes ->
                _stateUi.update { it.copy(notes = notes) }
            }
        }
    }

    fun addNote(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }

    fun remove(note: Note) {
        noteRepository.remove(note)
    }
}

data class NoteListStateUi(
    val notes: List<Note> = emptyList()
)