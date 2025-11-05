package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components.BookAddEditAppTopBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components.BookAddEditBottomBar
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components.BookAddEditContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAddEditScreen(
    modifier: Modifier = Modifier,
    viewModel: BookAddEditViewModel,
    onEvent: (BookAddEditEvent) -> Unit,
    onClickCoverImage: () -> Unit,
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BookAddEditAppTopBar(
                bookTitle = stateUi.book.title
            )
        },
        content = { paddingValues ->
            BookAddEditContent(
                modifier = modifier.padding(paddingValues),
                stateUi = stateUi,
                onCoverClick = onClickCoverImage,
                onEvent = onEvent
            )
        },
        bottomBar = {
            BookAddEditBottomBar(
                onClickSave = onClickSave,
                onClickCancel = onClickCancel
            )
        }
    )
}
