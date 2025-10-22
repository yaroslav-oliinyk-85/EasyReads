package com.oliinyk.yaroslav.easyreads.ui.screen.book.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsViewModel
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components.BookDetailsContent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components.BookDetailsTopAppBar

@Composable
fun BookDetailsScreen(
    viewModel: BookDetailsViewModel,
    onEvent: (BookDetailsEvent) -> Unit,
    onEditBook: () -> Unit,
    onRemoveBook: () -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    var showBookDeletingConfirmDialog by remember { mutableStateOf(false) }

    if (showBookDeletingConfirmDialog) {
        AppConfirmDialog(
            title = stringResource(R.string.book_details__confirmation_dialog__title_book_remove_text),
            message = viewModel.getCurrentBook().title,
            onConfirm = {
                onRemoveBook()
                showBookDeletingConfirmDialog = false
            },
            onDismiss = { showBookDeletingConfirmDialog = false }
        )
    }

    Scaffold(
        topBar = {
            BookDetailsTopAppBar(
                title = stringResource(R.string.book_details__toolbar__title_test),
                onEditBook = onEditBook,
                onRemoveBook = { showBookDeletingConfirmDialog = true }
            )
        }
    ) { paddingValues ->
        BookDetailsContent(
            stateUi = stateUi,
            onEvent = onEvent,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
