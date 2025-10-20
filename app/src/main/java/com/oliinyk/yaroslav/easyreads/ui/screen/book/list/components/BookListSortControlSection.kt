package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.domain.model.BookSorting
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingType
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookListSortControlSection(
    modifier: Modifier = Modifier,
    currentSorting: BookSorting,
    onSortingChange: (BookSortingType) -> Unit,
    onSortingOrderChange: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = Dimens.paddingVerticalSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SortByButtonWithDropdownMenu(
            modifier = Modifier.weight(1f),
            currentSorting = currentSorting,
            onSortingChange = onSortingChange
        )

        Spacer(modifier = Modifier.width(Dimens.spacerWidthSmall))

        SortOrderButton(onSortingOrderChange = onSortingOrderChange)
    }
}