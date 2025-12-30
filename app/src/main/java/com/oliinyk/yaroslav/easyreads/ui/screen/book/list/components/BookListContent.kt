package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingType
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.BookListUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookListContent(
    modifier: Modifier = Modifier,
    stateUi: BookListUiState,
    onBookClick: (Book) -> Unit,
    onSortingChange: (BookSortingType) -> Unit,
    onSortingOrderChange: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.paddingHorizontalMedium),
    ) {
        BookListSortControlSection(
            currentSorting = stateUi.bookSorting,
            onSortingChange = onSortingChange,
            onSortingOrderChange = onSortingOrderChange,
        )
        BookListSection(
            uiState = stateUi,
            onBookClick = onBookClick,
        )
    }
}
