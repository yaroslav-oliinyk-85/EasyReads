package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File

@Composable
fun ReadingGoalFinishedBooksItemCell(
    book: Book,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    size: DpSize = Dimens.readingGoalBookCoverImageSize,
) {
    Box(
        modifier =
            modifier
                .size(size)
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .background(MaterialTheme.colorScheme.background)
                .clickable { onBookClick(book) },
        contentAlignment = Alignment.Center,
    ) {
        val context = LocalContext.current
        book.coverImageFileName?.let { coverImageFileName ->
            val file = File(context.filesDir, coverImageFileName)
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model =
                    ImageRequest
                        .Builder(context)
                        .data(file)
                        .crossfade(true)
                        .build(),
                contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                contentScale = ContentScale.Crop,
            )
        } ?: Text(
            text = book.title,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = Dimens.readingGoalFinishedBooksItemCellTextMaxLines,
            modifier = Modifier.padding(Dimens.paddingAllTiny),
        )
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
