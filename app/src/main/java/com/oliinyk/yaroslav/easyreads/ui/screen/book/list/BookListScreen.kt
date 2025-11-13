package com.oliinyk.yaroslav.easyreads.ui.screen.book.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingOrderType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingType
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.presentation.book.list.BookListViewModel
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListTopAppBar

@Composable
fun BookListScreen(
    navToBookAdd: () -> Unit,
    navToBookDetails: (String) -> Unit,
    bookShelvesType: BookShelvesType? = null,
    viewModel: BookListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        bookShelvesType?.let { type ->
            viewModel.updateBookShelveType(type)
        }
    }

    val stateUi by viewModel.stateUi.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BookListTopAppBar(
                booksCount = stateUi.books.size,
                bookShelvesType = stateUi.bookShelvesType,
                holderSize = stateUi.holderSize,
                onAddBookClick = navToBookAdd,
                onHolderSizeChange = { viewModel.updateHolderSize(it) }
            )
        },
        content = { paddingValues ->
            BookListContent(
                modifier = Modifier.padding(paddingValues),
                stateUi = stateUi,
                onBookClick = { navToBookDetails(it.id.toString()) },
                onSortingChange = {
                    viewModel.updateBookSorting(
                        stateUi.bookSorting.copy(bookSortingType = it)
                    )
                },
                onSortingOrderChange = {
                    viewModel.updateBookSorting(
                        stateUi.bookSorting.copy(
                            bookSortingOrderType = if (
                                viewModel.bookSorting.bookSortingOrderType == BookSortingOrderType.DESC
                            ) {
                                BookSortingOrderType.ASC
                            } else {
                                BookSortingOrderType.DESC
                            }
                        )
                    )
                }
            )
        }
    )
}
