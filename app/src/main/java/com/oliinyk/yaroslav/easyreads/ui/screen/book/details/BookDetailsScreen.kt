package com.oliinyk.yaroslav.easyreads.ui.screen.book.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsViewModel
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components.BookDetailsContent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components.BookDetailsTopAppBar
import java.util.UUID

@Composable
fun BookDetailsScreen(
    bookId: UUID,
    navBack: () -> Unit,
    navToBookEdit: (String) -> Unit,
    navToReadingSessionRecord: (String) -> Unit,
    navToReadingSessionList: (String) -> Unit,
    navToNoteList: (String) -> Unit,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setup(bookId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showBookDeletingConfirmDialog by remember { mutableStateOf(false) }

    if (showBookDeletingConfirmDialog) {
        AppConfirmDialog(
            title = stringResource(R.string.book_details__confirmation_dialog__title_book_remove_text),
            message = uiState.book.title,
            onConfirm = {
                viewModel.removeCurrentBook()
                showBookDeletingConfirmDialog = false
                navBack()
            },
            onDismiss = { showBookDeletingConfirmDialog = false }
        )
    }

    Scaffold(
        topBar = {
            BookDetailsTopAppBar(
                title = stringResource(R.string.book_details__toolbar__title_test),
                onEditBook = { navToBookEdit(uiState.book.id.toString()) },
                onRemoveBook = { showBookDeletingConfirmDialog = true }
            )
        }
    ) { paddingValues ->
        BookDetailsContent(
            uiState = uiState,
            navToReadingSessionRecord = navToReadingSessionRecord,
            navToReadingSessionList = navToReadingSessionList,
            navToNoteList = navToNoteList,
            onEvent = { viewModel.handleEvent(it) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}
