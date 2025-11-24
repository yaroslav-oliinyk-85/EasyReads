package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppBadge
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.note.add_edit_dialog.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsNotesSection(
    notes: List<Note>,
    onSeeAllNotes: () -> Unit,
    onAddNote: (Note) -> Unit,
    onEditNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingNote: Note? by remember { mutableStateOf(null) }

    editingNote?.let { note ->
        NoteAddEditDialog(
            note = note,
            onSave = {
                if (it.bookId == null) onAddNote(it) else onEditNote(it)
                editingNote = null
            },
            onDismissRequest = { editingNote = null }
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingAllSmall)
        ) {
            if (notes.isEmpty()) {
                Text(
                    text = stringResource(R.string.book_details__label__no_notes_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                ShowLatestNoteInfoRow(
                    latestNote = notes.first(),
                    onClickEditNote = { latestNote ->
                        editingNote = latestNote
                    }
                )
            }

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            BottomActionButtonsRow(
                noteCount = notes.size,
                onClickSeeAllNotes = onSeeAllNotes,
                onClickAddNote = { editingNote = it }
            )
        }
    }
}

@Composable
private fun ShowLatestNoteInfoRow(
    latestNote: Note,
    onClickEditNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                // --- note text ---
                Text(
                    text = latestNote.text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // --- note added date text ---
                Text(
                    text = DateFormat.format(
                        stringResource(R.string.date_and_time_format),
                        latestNote.addedDate
                    ).toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                // --- note page text ---
                latestNote.page?.let { page ->
                    Text(
                        text = stringResource(
                            R.string.note_list_item__label__page_text,
                            page
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Spacer(Modifier.width(Dimens.spacerWidthSmall))
        // --- edit note icon button ---
        AppIconButton(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.menu_item__edit_text),
            onClick = { onClickEditNote(latestNote) }
        )
    }
}

@Composable
private fun BottomActionButtonsRow(
    noteCount: Int,
    onClickSeeAllNotes: () -> Unit,
    onClickAddNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- see all notes button ---
        AppTextButton(
            onClick = onClickSeeAllNotes,
            modifier = Modifier.weight(.5f)
        ) {
            Text(
                text = stringResource(
                    R.string.book_details__button__see_all_notes_text
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(Dimens.spacerWidthSmall))
            AppBadge(
                text = noteCount.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // --- add note button ---
        AppTextButton(
            onClick = {
                onClickAddNote(Note())
            },
            modifier = Modifier.weight(.5f)
        ) {
            Text(
                text = stringResource(R.string.book_details__button__add_note_text),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsNotesSectionPreview() {
    BookDetailsNotesSection(
        notes = listOf(
            Note(
                text = "Note Text",
                page = 5
            )
        ),
        onSeeAllNotes = {},
        onAddNote = {},
        onEditNote = {}
    )
}
@Preview(showBackground = true)
@Composable
private fun BookDetailsNotesSectionEmptyPreview() {
    BookDetailsNotesSection(
        notes = emptyList(),
        onSeeAllNotes = {},
        onAddNote = {},
        onEditNote = {}
    )
}