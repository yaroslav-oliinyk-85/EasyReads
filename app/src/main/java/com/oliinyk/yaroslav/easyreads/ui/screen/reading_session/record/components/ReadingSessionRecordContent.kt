package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordEvent
import com.oliinyk.yaroslav.easyreads.presentation.reading_session.record.ReadingSessionRecordUiState
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.note.add_edit.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit.ReadingSessionAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingSessionRecordContent(
    stateUi: ReadingSessionRecordUiState,
    onEvent: (ReadingSessionRecordEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingReadingSession: ReadingSession? by remember { mutableStateOf(null) }
    editingReadingSession?.let { readingSession ->
        ReadingSessionAddEditDialog(
            readingSession = readingSession,
            onSave = { it
                onEvent(ReadingSessionRecordEvent.OnFinish(it))
                editingReadingSession = null
            },
            onDismissRequest = { editingReadingSession = null }
        )
    }

    var addingNote: Note? by remember { mutableStateOf(null) }
    addingNote?.let { note ->
        NoteAddEditDialog(
            note = note,
            onSave = {
                onEvent(ReadingSessionRecordEvent.OnAddNote(it))
                addingNote = null
            },
            onDismissRequest = { addingNote = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.paddingAllMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
    ) {
        ReadingSessionRecordBookCoverSection(
            book = stateUi.book ?: Book()
        )

        ReadingSessionRecordSection(
            readingSession = stateUi.readingSession ?: ReadingSession(),
            onStartPause = { onEvent(ReadingSessionRecordEvent.OnStartPause) }
        )

        Row(Modifier.fillMaxWidth()) {
            // --- See All Notes Button ---
            AppTextButton(
                onClick = { onEvent(ReadingSessionRecordEvent.OnShowNotes) },
                modifier = Modifier
                    .weight(.5f)
            ) {
                Text(
                    text = stringResource(
                        R.string.reading_session_record__button__show_notes_text,
                        stateUi.noteCount
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.width(Dimens.spacerWidthSmall))

            // --- Add Note Button ---
            AppTextButton(
                onClick = { addingNote = Note().copy(bookId = stateUi.book?.id) },
                modifier = Modifier.weight(.5f)
            ) {
                Text(
                    text = stringResource(R.string.reading_session_record__button__add_note_text),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // --- Finish Button ---
        AppTextButton(
            onClick = {
                onEvent(ReadingSessionRecordEvent.OnPause)
                editingReadingSession = stateUi.readingSession
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.reading_session_record__button__finish_recording_text),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordContentPreview() {
    EasyReadsTheme {
        ReadingSessionRecordContent(
            stateUi = ReadingSessionRecordUiState(),
            onEvent = {}
        )
    }
}