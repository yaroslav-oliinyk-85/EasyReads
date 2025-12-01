package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.util.Date

@Composable
fun BookDetailsContent(
    uiState: BookDetailsUiState,
    onEvent: (BookDetailsEvent) -> Unit,
    navToReadingSessionRecord: (String) -> Unit,
    navToReadingSessionList: (String) -> Unit,
    navToNoteList: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.paddingHorizontalMedium, vertical = Dimens.spacerHeightMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
    ) {
        BookDetailsCoverSection(
            book = uiState.book,
            uiState = uiState,
            progressPercentage = uiState.percentage,
            onClickShelf = { shelf ->
                onEvent(BookDetailsEvent.ShelfChanged(shelf))
            },
        )
        BookDetailsReadingSessionsSection(
            sessions = uiState.readingSessions,
            isBookFinished = uiState.book.isFinished,
            onStartReadingSession = { navToReadingSessionRecord(uiState.book.id.toString()) },
            onSeeAll = { navToReadingSessionList(uiState.book.id.toString()) },
            onEdit = { onEvent(BookDetailsEvent.EditReadingSession(it)) },
        )
        BookDetailsNotesSection(
            notes = uiState.notes,
            onSeeAllNotes = { navToNoteList(uiState.book.id.toString()) },
            onAddNote = { onEvent(BookDetailsEvent.AddNote(it)) },
            onEditNote = { onEvent(BookDetailsEvent.EditNote(it)) },
        )
        BookDetailsIsbnSection(isbn = uiState.book.isbn)
        BookDetailsDescriptionSection(uiState.book.description)
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsContentFinishedPreview() {
    EasyReadsTheme {
        BookDetailsContent(
            uiState =
                BookDetailsUiState(
                    book =
                        Book(
                            title = "Title",
                            author = "Author",
                            pageAmount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.FINISHED,
                            isFinished = true,
                            finishedDate = Date(),
                        ),
                ),
            onEvent = {},
            navToNoteList = {},
            navToReadingSessionList = {},
            navToReadingSessionRecord = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsContentReadingPreview() {
    EasyReadsTheme {
        BookDetailsContent(
            uiState =
                BookDetailsUiState(
                    book =
                        Book(
                            title = "Title",
                            author = "Author",
                            pageAmount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.READING,
                            isFinished = false,
                        ),
                ),
            onEvent = {},
            navToNoteList = {},
            navToReadingSessionList = {},
            navToReadingSessionRecord = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsContentWantToReadPreview() {
    EasyReadsTheme {
        BookDetailsContent(
            uiState =
                BookDetailsUiState(
                    book =
                        Book(
                            title = "Title",
                            author = "Author",
                            pageAmount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.WANT_TO_READ,
                            isFinished = false,
                        ),
                ),
            onEvent = {},
            navToNoteList = {},
            navToReadingSessionList = {},
            navToReadingSessionRecord = {},
        )
    }
}
