package com.oliinyk.yaroslav.easyreads.ui.screen.book.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingType
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.presentation.book.list.BookListViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListTopAppBar

@Composable
fun BookListScreen(
    viewModel: BookListViewModel,
    onBookClick: (Book) -> Unit,
    onAddBookClick: () -> Unit,
    onHolderSizeChange: (HolderSize) -> Unit,
    onSortingChange: (BookSortingType) -> Unit,
    onSortingOrderChange: () -> Unit
) {
    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BookListTopAppBar(
                stateUi = stateUi,
                onAddBookClick = onAddBookClick,
                onHolderSizeChange = onHolderSizeChange
            )
        },
        content = { paddingValues ->
            BookListContent(
                modifier = Modifier.padding(paddingValues),
                stateUi = stateUi,
                onBookClick = onBookClick,
                onSortingChange = onSortingChange,
                onSortingOrderChange = onSortingOrderChange
            )
        }
    )
}
