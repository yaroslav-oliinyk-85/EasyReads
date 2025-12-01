package com.oliinyk.yaroslav.easyreads.ui.screen.note.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.note.addeditdialog.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListTopAppBar
import java.util.UUID

@Composable
fun NoteListScreen(
    bookId: String?,
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(UUID.fromString(id))
        }
    }

    val stateUi: NoteListStateUi by viewModel.stateUi.collectAsStateWithLifecycle()
    val editingNote: Note? by viewModel.editingNote.collectAsStateWithLifecycle()
    val removingNote: Note? by viewModel.removingNote.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NoteListTopAppBar(
                noteSize = stateUi.notes.size,
                onAdd = {
                    viewModel.openAddDialog()
                },
            )
        },
    ) { paddingValues ->
        NoteListContent(
            notes = stateUi.notes,
            onEdit = { note ->
                viewModel.openEditDialog(note)
            },
            onRemove = { note ->
                viewModel.openRemoveDialog(note)
            },
            modifier = Modifier.padding(paddingValues),
        )

        editingNote?.let { note ->
            NoteAddEditDialog(
                note = note,
                onSave = {
                    viewModel.save(it)
                    viewModel.dismissAddEditDialog()
                },
                onDismissRequest = { viewModel.dismissAddEditDialog() },
            )
        }

        removingNote?.let { note ->
            AppConfirmDialog(
                title = stringResource(R.string.note_list__confirmation_dialog__title_text),
                message = note.text,
                onConfirm = {
                    viewModel.remove(note)
                    viewModel.dismissRemoveDialog()
                },
                onDismiss = { viewModel.dismissRemoveDialog() },
            )
        }
    }
}
