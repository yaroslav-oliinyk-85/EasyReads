package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingGoalFinishedBooksSection(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        horizontal = Dimens.paddingHorizontalMedium,
                        vertical = Dimens.paddingVerticalSmall,
                    ),
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text =
                        stringResource(
                            R.string.reading_goal__label__summery_books_title_text,
                            books.size,
                        ),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            Spacer(Modifier.height(Dimens.spacerHeightSmall))

            if (books.isEmpty()) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.reading_goal__label__no_finished_books_text),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(Dimens.gridCellsCount),
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceTiny),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceTiny),
                    content = {
                        items(books) { book ->
                            ReadingGoalFinishedBooksItemCell(
                                book = book,
                                onBookClick = onBookClick,
                            )
                        }
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingGoalFinishedBooksSectionEmptyListPreview() {
    EasyReadsTheme {
        ReadingGoalFinishedBooksSection(
            books = emptyList(),
            onBookClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingGoalFinishedBooksSectionPreview() {
    EasyReadsTheme {
        ReadingGoalFinishedBooksSection(
            books =
                listOf(
                    Book(title = "Title title title title title title title title title title aaa"),
                    Book(title = "The Final Empire"),
                    Book(title = "The Well Of Ascension"),
                    Book(title = "TheWellOfAscension"),
                ),
            onBookClick = {},
        )
    }
}
