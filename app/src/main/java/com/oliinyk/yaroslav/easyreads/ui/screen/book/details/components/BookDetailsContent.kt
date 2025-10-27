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
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsContent(
    stateUi: BookDetailsStateUi,
    onEvent: (BookDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.paddingHorizontalMedium, vertical = Dimens.spacerHeightMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
    ) {
        BookDetailsCoverSection(book = stateUi.book)
        BookDetailsProgressSection(
            stateUi = stateUi,
            onStartReadingSession = { onEvent(BookDetailsEvent.StartReadingSession) },
            onSeeAll = { onEvent(BookDetailsEvent.SeeAllReadingSessions) }
        )
        if (!stateUi.book.isFinished) {
            BookDetailsReadingSessionsSection(
                sessions = stateUi.readingSessions,
                onSeeAll = { onEvent(BookDetailsEvent.SeeAllReadingSessions) },
                onEdit = { onEvent(BookDetailsEvent.EditReadingSession(it)) }
            )
        }
        BookDetailsNotesSection(
            notes = stateUi.notes,
            onSeeAllNotes = { onEvent(BookDetailsEvent.SeeAllNotes) },
            onAddNote = { onEvent(BookDetailsEvent.AddNote(it)) },
            onEditNote = { onEvent(BookDetailsEvent.EditNote(it)) }
        )
        BookDetailsIsbnSection(isbn = stateUi.book.isbn)
        BookDetailsDescriptionSection(stateUi.book.description)
    }
}