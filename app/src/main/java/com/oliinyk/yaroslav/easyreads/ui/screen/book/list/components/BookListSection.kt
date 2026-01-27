package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.BookListUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookListSection(
    modifier: Modifier = Modifier,
    uiState: BookListUiState,
    onBookClick: (Book) -> Unit,
) {
    if (uiState.books.isEmpty()) {
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.book_list__label__no_books_text),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
        ) {
            items(uiState.books, key = { it.id }) { book ->
                BookListItem(
                    book = book,
                    holderSize = uiState.holderSize,
                    onClickedBook = onBookClick,
                )
            }
            item {
                Spacer(Modifier.height(Dimens.spacerHeightSmall))
            }
            item {
                Spacer(Modifier.height(Dimens.spacerHeightExtraLarge))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookListSectionEmptyPreview() {
    BookListSection(
        uiState = BookListUiState(),
        onBookClick = { },
    )
}

@Preview(showBackground = true)
@Composable
private fun BookListSectionPreview() {
    BookListSection(
        uiState = BookListUiState(books = listOf(Book(title = "Title", author = "Author"))),
        onBookClick = { },
    )
}
