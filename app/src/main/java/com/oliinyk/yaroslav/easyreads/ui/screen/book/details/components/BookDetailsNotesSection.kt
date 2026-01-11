package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.note.addeditdialog.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components.NoteListItemCell
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun BookDetailsNotesSection(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onEditNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    var editingNote: Note? by rememberSaveable { mutableStateOf(null) }
    var removingNote: Note? by rememberSaveable { mutableStateOf(null) }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        // --- add note button ---
        AppButton(
            onClick = {
                editingNote = Note()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.book_details__button__add_note_text),
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(Modifier.height(Dimens.spacerHeightSmall))

        NoteList(
            notes = notes,
            onEditNote = {
                editingNote = it
            },
        )
    }

    // ----- Dialogs -----

    editingNote?.let { note ->
        NoteAddEditDialog(
            note = note,
            isRemoveButtonEnabled = note.bookId != null,
            onSave = {
                if (it.bookId == null) onAddNote(it) else onEditNote(it)
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
                onRemoveNote(note)
                removingNote = null
            },
            onDismiss = { removingNote = null },
        )
    }
}

@Composable
private fun NoteList(
    notes: List<Note>,
    onEditNote: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
    ) {
        notes.forEach { note ->
            NoteListItemCell(
                note = note,
                onEdit = { onEditNote(it) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsNotesSectionPreview() {
    EasyReadsTheme {
        BookDetailsNotesSection(
            notes =
                listOf(
                    Note(
                        text = "Note Text 1",
                        page = 5,
                    ),
                    Note(
                        text = "Note Text 2",
                        page = 15,
                    ),
                    Note(
                        text = "Note Text 3",
                        page = 25,
                    ),
                ),
            onAddNote = {},
            onEditNote = {},
            onRemoveNote = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsNotesSectionEmptyPreview() {
    EasyReadsTheme {
        BookDetailsNotesSection(
            notes = emptyList(),
            onAddNote = {},
            onEditNote = {},
            onRemoveNote = {},
        )
    }
}
