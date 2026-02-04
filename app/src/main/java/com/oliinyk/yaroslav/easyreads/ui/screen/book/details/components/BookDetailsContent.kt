package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.LocalDateTime

@Composable
fun BookDetailsContent(
    uiState: BookDetailsUiState,
    onEvent: (BookDetailsEvent) -> Unit,
    navToReadingSessionRecord: (String) -> Unit,
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
            progressPercentage = uiState.percentage,
            onClickShelf = { shelf ->
                onEvent(BookDetailsEvent.ShelfChanged(shelf))
            },
        )

        var selectedTab by rememberSaveable { mutableStateOf(BookDetailTabs.SESSIONS) }

        PrimaryTabRow(selectedTabIndex = selectedTab.ordinal) {
            BookDetailTabs.entries.forEachIndexed { index, bookDetailTab ->
                Tab(
                    selected = selectedTab.ordinal == index,
                    onClick = { selectedTab = BookDetailTabs.entries[index] },
                    text = {
                        Text(
                            text =
                                when (bookDetailTab) {
                                    BookDetailTabs.SESSIONS -> {
                                        stringResource(
                                            R.string.book_details__tab__sessions_title_text,
                                            uiState.readingSessions.size,
                                        )
                                    }
                                    BookDetailTabs.NOTES -> {
                                        stringResource(
                                            R.string.book_details__tab__notes_title_text,
                                            uiState.notes.size,
                                        )
                                    }
                                    BookDetailTabs.ABOUT -> {
                                        stringResource(R.string.book_details__tab__about_title_text)
                                    }
                                },
                            maxLines = Dimens.bookDetailsTabTitleMaxLines,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
            }
        }

        when (selectedTab) {
            BookDetailTabs.SESSIONS -> {
                BookDetailsReadingSessionsSection(
                    uiState = uiState,
                    isReading = uiState.book.shelf == BookShelvesType.READING,
                    onStartReadingSession = { navToReadingSessionRecord(uiState.book.id.toString()) },
                    onEditClicked = { onEvent(BookDetailsEvent.UpdateReadingSession(it)) },
                    onRemoveClicked = { onEvent(BookDetailsEvent.RemoveReadingSession(it)) },
                )
            }
            BookDetailTabs.NOTES -> {
                BookDetailsNotesSection(
                    notes = uiState.notes,
                    pagesCount = uiState.book.pagesCount,
                    onAddNote = { onEvent(BookDetailsEvent.AddNote(it)) },
                    onEditNote = { onEvent(BookDetailsEvent.UpdateNote(it)) },
                    onRemoveNote = { onEvent(BookDetailsEvent.RemoveNote(it)) },
                )
            }
            BookDetailTabs.ABOUT -> {
                BookDetailsIsbnSection(isbn = uiState.book.isbn)
                BookDetailsDescriptionSection(uiState.book.description)
            }
        }

        Spacer(Modifier.height(Dimens.spacerHeightExtraLarge))
    }
}

private enum class BookDetailTabs {
    SESSIONS,
    NOTES,
    ABOUT,
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
                            pagesCount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.FINISHED,
                            isFinished = true,
                            finishedAt = LocalDateTime.now(),
                        ),
                ),
            onEvent = {},
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
                            pagesCount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.READING,
                            isFinished = false,
                        ),
                ),
            onEvent = {},
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
                            pagesCount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.WANT_TO_READ,
                            isFinished = false,
                        ),
                ),
            onEvent = {},
            navToReadingSessionRecord = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsContentReadingSessionsPreview() {
    EasyReadsTheme {
        BookDetailsContent(
            uiState =
                BookDetailsUiState(
                    book =
                        Book(
                            title = "Title",
                            author = "Author",
                            pagesCount = 250,
                            pageCurrent = 50,
                            shelf = BookShelvesType.READING,
                            isFinished = false,
                        ),
                    readingSessions =
                        listOf(
                            ReadingSession(),
                            ReadingSession(),
                            ReadingSession(),
                            ReadingSession(),
                            ReadingSession(),
                        ),
                ),
            onEvent = {},
            navToReadingSessionRecord = {},
        )
    }
}
