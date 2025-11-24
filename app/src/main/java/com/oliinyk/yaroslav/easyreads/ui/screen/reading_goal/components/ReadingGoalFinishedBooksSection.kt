package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.components.AppBadge
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingGoalFinishedBooksSection(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        val gridCellsCount = 4
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall
                )
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.reading_goal__label__summery_books_title_text, books.size
                    ),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.width(Dimens.spacerWidthSmall))
                AppBadge(
                    text = books.size.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            Spacer(Modifier.height(Dimens.spacerHeightSmall))

            LazyVerticalGrid(
                columns = GridCells.Fixed(gridCellsCount),
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceTiny),
                horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceTiny),
                content = {
                    items(books) { book ->
                        ReadingGoalFinishedBooksItemCell(
                            book = book,
                            onBookClick = onBookClick
                        )
                    }
                }
            )
        }
    }
}
