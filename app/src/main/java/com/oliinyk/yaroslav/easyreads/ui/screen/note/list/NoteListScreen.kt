package com.oliinyk.yaroslav.easyreads.ui.screen.note.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.components.AppFloatingActionButton
import com.oliinyk.yaroslav.easyreads.ui.screen.note.addeditdialog.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.util.UUID

@Composable
fun NoteListScreen(
    bookId: String?,
    navBack: () -> Unit,
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        bookId?.let { id ->
            viewModel.setup(UUID.fromString(id))
        }
    }

    val uiState: NoteListStateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    NoteListScreen(
        uiState = uiState,
        navBack = navBack,
        onSave = viewModel::save,
        onRemove = viewModel::remove,
    )
}

@Composable
fun NoteListScreen(
    uiState: NoteListStateUi,
    navBack: () -> Unit,
    onSave: (Note) -> Unit,
    onRemove: (Note) -> Unit,
) {
    var editingNote: Note? by rememberSaveable { mutableStateOf(null) }
    var removingNote: Note? by rememberSaveable { mutableStateOf(null) }

    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NoteListTopAppBar(
                noteSize = uiState.notes.size,
                navBack = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navBack()
                    }
                },
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = {
                    editingNote = Note()
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.menu_item__add_text),
                    )
                },
            )
        },
    ) { paddingValues ->
        NoteListContent(
            notes = uiState.notes,
            onEdit = { note ->
                editingNote = note
            },
            modifier = Modifier.padding(paddingValues),
        )
    }

    // ----- Dialogs -----

    editingNote?.let { note ->
        NoteAddEditDialog(
            note = note,
            isRemoveButtonEnabled = (note.bookId != null),
            onSave = {
                onSave(it)
                editingNote = null
            },
            onRemove = {
                editingNote = null
                removingNote = it
            },
            onDismissRequest = { editingNote = null },
        )
    }

    removingNote?.let { note ->
        AppConfirmDialog(
            title = stringResource(R.string.note_list__confirmation_dialog__title_text),
            message = note.text,
            onConfirm = {
                onRemove(note)
                removingNote = null
            },
            onDismiss = { removingNote = null },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenPreview() {
    EasyReadsTheme {
        NoteListScreen(
            uiState =
                NoteListStateUi(
                    notes =
                        listOf(
                            Note(text = "Note 1", page = 5),
                            Note(text = "Note 2", page = 10),
                            Note(text = "Note 3", page = 15),
                        ),
                ),
            navBack = {},
            onSave = {},
            onRemove = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenEmptyPreview() {
    EasyReadsTheme {
        NoteListScreen(
            uiState = NoteListStateUi(),
            navBack = {},
            onSave = {},
            onRemove = {},
        )
    }
}
