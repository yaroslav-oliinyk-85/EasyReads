package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File

@Composable
fun FinishedBooksCard(
    modifier: Modifier,
    books: List<Book>,
    onBookClick: (Book) -> Unit
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        val gridCellsCount = 4
        Column(
            modifier = modifier
                .padding(
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall
                )
        ) {
            Text(
                text = stringResource(R.string.reading_goal__label__summery_books_title_text, books.size),
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            Spacer(modifier.height(Dimens.spacerHeightSmall))

            LazyVerticalGrid(
                columns = GridCells.Fixed(gridCellsCount),
                modifier = modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
                horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall),
                content = {
                    items(books) { book ->
                        FinishedBooksGridItem(
                            modifier = modifier,
                            book = book,
                            onBookClick = onBookClick
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun FinishedBooksGridItem(
    modifier: Modifier,
    book: Book,
    onBookClick: (Book) -> Unit,
    size: DpSize = Dimens.finishedBookCoverImageSize // default, you can add SMALL/LARGE variants
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onBookClick(book) }
    ) {
        val context = LocalContext.current
        book.coverImageFileName?.let { coverImageFileName ->
            val file = File(context.filesDir, coverImageFileName)
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(file)
                    .build(),
                contentDescription = book.title,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

/*
@Composable
fun LocalFileImage(filePath: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("file://$filePath")
            .crossfade(true)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .build(),
        contentDescription = "Local file image"
    )
}

 */
