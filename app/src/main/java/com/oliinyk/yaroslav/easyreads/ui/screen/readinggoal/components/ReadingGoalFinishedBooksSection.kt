package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Text(
                        text = stringResource(R.string.reading_goal__label__no_finished_books_text),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                FlowRow(
                    maxItemsInEachRow = Dimens.gridCellsCount,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall),
                    verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
                ) {
                    books.forEach { book ->
                        ReadingGoalFinishedBooksItemCell(
                            book = book,
                            onBookClick = onBookClick,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    repeat(Dimens.gridCellsPlaceHolderCount) {
                        Spacer(Modifier.weight(1f))
                    }
                }
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
