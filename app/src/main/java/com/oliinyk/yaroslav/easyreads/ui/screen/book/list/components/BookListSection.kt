package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.StateUiBookList
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookListSection(
    modifier: Modifier = Modifier,
    stateUi: StateUiBookList,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
    ) {
        items(stateUi.books) { book ->
            BookListItem(
                book = book,
                holderSize = stateUi.holderSize,
                onClickedBook = onBookClick
            )
        }
        item {
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
        }
    }
}