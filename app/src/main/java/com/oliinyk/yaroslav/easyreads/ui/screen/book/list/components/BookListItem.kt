package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelveType
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.ui.components.ReadingProgressIndicator
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    book: Book,
    holderSize: HolderSize = HolderSize.DEFAULT,
    onClickedBook: (Book) -> Unit
) {
    val percentage = if (book.pageAmount != 0) {
        (book.pageCurrent * 100 / book.pageAmount)
    } else { 0 }

    var coverImageWidth: Dp
    var bookListItemHeight: Dp

    var titleMaxLines: Int
    var authorMaxLines: Int

    when(holderSize) {
        HolderSize.SMALL -> {
            coverImageWidth = 74.dp
            bookListItemHeight = 111.dp
            titleMaxLines = 1
            authorMaxLines = 1
        }
        HolderSize.LARGE -> {
            coverImageWidth = 132.dp
            bookListItemHeight = 198.dp
            titleMaxLines = 3
            authorMaxLines = 2
        }
        HolderSize.DEFAULT -> {
            coverImageWidth = 92.dp
            bookListItemHeight = 138.dp
            titleMaxLines = 2
            authorMaxLines = 1
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClickedBook(book) }
            .padding(Dimens.paddingAllSmall)
    ) {
        // Book Cover Image
        val context = LocalContext.current
        val file: File? = if (book.coverImageFileName != null) {
            File(context.filesDir, book.coverImageFileName)
        } else { null }
        AsyncImage(
            modifier = Modifier
                .width(coverImageWidth)
                .height(bookListItemHeight)
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .background(MaterialTheme.colorScheme.background),
            model = ImageRequest.Builder(context)
                .data(file)
                .build(),
            contentDescription = stringResource(R.string.book_cover_image__content_description__text),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(Dimens.spacerWidthSmall))

        Column(
            modifier = Modifier
                .weight(1f)
                .height(bookListItemHeight)
                .align(Alignment.Top)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = titleMaxLines,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = authorMaxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = Dimens.paddingTopTiny)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shelve
                Text(
                    modifier = Modifier.padding(end = Dimens.paddingEndTiny),
                    text = stringResource(
                        R.string.book_details__label__shelve_text,
                        when (book.shelve) {
                        BookShelveType.WANT_TO_READ -> stringResource(R.string.book_details__label__shelve_want_to_read_text)
                        BookShelveType.READING -> stringResource(R.string.book_details__label__shelve_reading_text)
                        BookShelveType.FINISHED -> stringResource(R.string.book_details__label__shelve_finished_text)
                    }),
                    style = MaterialTheme.typography.bodyMedium
                )
                // Pages
                Text(
                    modifier = Modifier.weight(1f)
                        .padding(end = Dimens.paddingEndSmall),
                    text = stringResource(
                        R.string.book_details__label__book_pages_text,
                        book.pageCurrent,
                        book.pageAmount
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End
                )
                ReadingProgressIndicator(percentage = percentage)
            }
        }
    }
}
