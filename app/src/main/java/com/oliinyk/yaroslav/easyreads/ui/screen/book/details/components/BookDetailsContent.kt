package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsContent(
    stateUi: BookDetailsStateUi,
    onEvent: (BookDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.paddingHorizontalMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
    ) {
        item {
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
        }
        item {
            BookDetailsCoverSection(book = stateUi.book)
        }
        item {
            BookDetailsProgressSection(
                stateUi = stateUi,
                onStartReadingSession = { onEvent(BookDetailsEvent.StartReadingSession) },
                onSeeAll = { onEvent(BookDetailsEvent.SeeAllReadingSessions) }
            )
        }
        if (!stateUi.book.isFinished) {
            item {
                BookDetailsReadingSessionsSection(
                    sessions = stateUi.readingSessions,
                    onSeeAll = { onEvent(BookDetailsEvent.SeeAllReadingSessions) },
                    onEdit = { onEvent(BookDetailsEvent.EditReadingSession(it)) }
                )
            }
        }
        item {
            BookDetailsNotesSection(
                notes = stateUi.notes,
                onSeeAllNotes = { onEvent(BookDetailsEvent.SeeAllNotes) },
                onAddNote = { onEvent(BookDetailsEvent.AddNote(it)) },
                onEditNote = { onEvent(BookDetailsEvent.EditNote(it)) }
            )
        }
        item {
            BookDetailsIsbnSection(isbn = stateUi.book.isbn)
        }
        item {
            BookDetailsDescriptionSection(stateUi.book.description)
        }
        item {
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
        }
    }
}