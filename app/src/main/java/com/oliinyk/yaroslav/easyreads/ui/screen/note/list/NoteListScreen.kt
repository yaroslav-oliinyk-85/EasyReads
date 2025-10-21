package com.oliinyk.yaroslav.easyreads.ui.screen.note.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.presentation.note.list.NoteListStateUi
import com.oliinyk.yaroslav.easyreads.presentation.note.list.NoteListViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListTopAppBar

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel,
    onAdd: () -> Unit,
    onEdit: (Note) -> Unit,
    onRemove: (Note) -> Unit
) {
    val stateUi: NoteListStateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NoteListTopAppBar(
                noteSize = stateUi.notes.size,
                onAdd = onAdd
            )
        }
    ) { paddingValues ->
        NoteListContent(
            stateUi = stateUi,
            onEdit = onEdit,
            onRemove = onRemove,
            modifier = Modifier.padding(paddingValues)
        )
    }
}