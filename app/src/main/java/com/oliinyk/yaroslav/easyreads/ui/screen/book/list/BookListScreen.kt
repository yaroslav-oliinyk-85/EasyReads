package com.oliinyk.yaroslav.easyreads.ui.screen.book.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingOrderType
import com.oliinyk.yaroslav.easyreads.ui.components.AppFloatingActionButton
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListContent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components.BookListTopAppBar

@Composable
fun BookListScreen(
    navBack: () -> Unit,
    navToBookAdd: () -> Unit,
    navToBookDetails: (String) -> Unit,
    bookShelvesType: BookShelvesType? = null,
    viewModel: BookListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        bookShelvesType?.let { type ->
            viewModel.updateBookShelveType(type)
        }
    }

    val stateUi by viewModel.uiState.collectAsStateWithLifecycle()
    var isTriggeredNavTo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BookListTopAppBar(
                booksCount = stateUi.books.size,
                bookShelvesType = stateUi.bookShelvesType,
                holderSize = stateUi.holderSize,
                navBack = navBack,
                onHolderSizeChange = { viewModel.updateHolderSize(it) },
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookAdd()
                    }
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.menu_item__add_text),
                    )
                },
            )
        },
        content = { paddingValues ->
            BookListContent(
                modifier = Modifier.padding(paddingValues),
                stateUi = stateUi,
                onBookClick = {
                    if (!isTriggeredNavTo) {
                        isTriggeredNavTo = true
                        navToBookDetails(it.id.toString())
                    }
                },
                onSortingChange = {
                    viewModel.updateBookSorting(
                        stateUi.bookSorting.copy(bookSortingType = it),
                    )
                },
                onSortingOrderChange = {
                    viewModel.updateBookSorting(
                        stateUi.bookSorting.copy(
                            bookSortingOrderType =
                                if (
                                    viewModel.bookSorting.bookSortingOrderType == BookSortingOrderType.DESC
                                ) {
                                    BookSortingOrderType.ASC
                                } else {
                                    BookSortingOrderType.DESC
                                },
                        ),
                    )
                },
            )
        },
    )
}
