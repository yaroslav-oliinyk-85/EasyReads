package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.ReadingSessionRecordEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.ReadingSessionRecordUiState
import com.oliinyk.yaroslav.easyreads.ui.screen.note.add_edit_dialog.NoteAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit_dialog.ReadingSessionAddEditDialog
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
            onSave = {
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
            notesCount = stateUi.notesCount,
            onClickStartPause = { onEvent(ReadingSessionRecordEvent.OnStartPause) },
            onClickShowNotes = { onEvent(ReadingSessionRecordEvent.OnShowNotes) },
            onClickAddNote = { addingNote = Note().copy(bookId = stateUi.book?.id) },
            onClickFinish = {
                onEvent(ReadingSessionRecordEvent.OnPause)
                editingReadingSession = stateUi.readingSession
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordContentPreview() {
    EasyReadsTheme {
        ReadingSessionRecordContent(
            stateUi = ReadingSessionRecordUiState(
                book = Book(
                    title = "Title",
                    author = "Author"
                ),
                readingSession = ReadingSession(),
                notesCount = 5
            ),
            onEvent = {}
        )
    }
}