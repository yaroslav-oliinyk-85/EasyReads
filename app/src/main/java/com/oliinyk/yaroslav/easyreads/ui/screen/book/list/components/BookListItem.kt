package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.annotation.StringRes
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.ui.components.ReadingProgressIndicator
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BookListItem(
    book: Book,
    onClickedBook: (Book) -> Unit,
    modifier: Modifier = Modifier,
    holderSize: HolderSize = HolderSize.DEFAULT,
) {
    val percentage =
        if (book.pagesCount != 0) {
            (book.pageCurrent * 100 / book.pagesCount)
        } else {
            0
        }

    var coverImageWidth: Dp
    var bookListItemHeight: Dp

    var titleMaxLines: Int
    var authorMaxLines: Int
    var dateFormatStringResourceId: Int

    when (holderSize) {
        HolderSize.SMALL -> {
            coverImageWidth = Dimens.sizeSmallBookListItemCoverImageWidth
            bookListItemHeight = Dimens.sizeSmallBookListItemHeight
            titleMaxLines = Dimens.sizeSmallBookListItemTitleMaxLines
            authorMaxLines = Dimens.sizeSmallBookListItemAuthorMaxLines
            dateFormatStringResourceId = R.string.date_format
        }
        HolderSize.DEFAULT -> {
            coverImageWidth = Dimens.sizeMediumBookListItemCoverImageWidth
            bookListItemHeight = Dimens.sizeMediumBookListItemHeight
            titleMaxLines = Dimens.sizeMediumBookListItemTitleMaxLines
            authorMaxLines = Dimens.sizeMediumBookListItemAuthorMaxLines
            dateFormatStringResourceId = R.string.date_format
        }
        HolderSize.LARGE -> {
            coverImageWidth = Dimens.sizeLargeBookListItemCoverImageWidth
            bookListItemHeight = Dimens.sizeLargeBookListItemHeight
            titleMaxLines = Dimens.sizeLargeBookListItemTitleMaxLines
            authorMaxLines = Dimens.sizeLargeBookListItemAuthorMaxLines
            dateFormatStringResourceId = R.string.date_format
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .clickable { onClickedBook(book) }
                    .padding(Dimens.paddingAllSmall),
        ) {
            BookCoverImage(
                book = book,
                coverImageWidth = coverImageWidth,
                bookListItemHeight = bookListItemHeight,
            )
            Spacer(modifier = Modifier.width(Dimens.spacerWidthSmall))
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .height(bookListItemHeight)
                        .align(Alignment.Top),
            ) {
                TitleText(
                    text = book.title,
                    maxLines = titleMaxLines,
                )
                AuthorText(
                    text = book.author,
                    maxLines = authorMaxLines,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ShelfText(
                        modifier = Modifier.weight(1f),
                        book = book,
                        dateFormatStringResourceId = dateFormatStringResourceId,
                    )
                    PagesText(book = book)
                    ReadingProgressIndicator(percentage = percentage)
                }
            }
        }
    }
}

@Composable
private fun BookCoverImage(
    book: Book,
    coverImageWidth: Dp,
    bookListItemHeight: Dp,
) {
    val context = LocalContext.current
    val file: File? =
        if (book.coverImageFileName != null) {
            File(context.filesDir, book.coverImageFileName)
        } else {
            null
        }
    AsyncImage(
        modifier =
            Modifier
                .width(coverImageWidth)
                .height(bookListItemHeight)
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .background(MaterialTheme.colorScheme.background),
        model =
            ImageRequest
                .Builder(context)
                .data(file)
                .crossfade(true)
                .build(),
        contentDescription = stringResource(R.string.book_cover_image__content_description__text),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun TitleText(
    text: String,
    maxLines: Int,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun AuthorText(
    text: String,
    maxLines: Int,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = Dimens.paddingTopTiny),
    )
}

@Composable
private fun ShelfText(
    book: Book,
    @StringRes
    dateFormatStringResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(end = Dimens.paddingEndTiny),
            text =
                when (book.shelf) {
                    BookShelvesType.FINISHED ->
                        stringResource(
                            R.string.book_list_item__label__shelf_finished_text,
                        )
                    BookShelvesType.READING ->
                        stringResource(
                            R.string.book_list_item__label__shelf_reading_text,
                        )
                    BookShelvesType.WANT_TO_READ ->
                        stringResource(
                            R.string.book_list_item__label__shelf_want_to_read_text,
                        )
                },
            maxLines = Dimens.bookListItemShelveTextMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier.padding(end = Dimens.paddingEndTiny),
            text =
                when (book.shelf) {
                    BookShelvesType.FINISHED ->
                        book.finishedAt?.format(
                            DateTimeFormatter.ofPattern(
                                stringResource(dateFormatStringResourceId),
                            ),
                        ) ?: ""
                    BookShelvesType.READING ->
                        book.updatedAt.format(
                            DateTimeFormatter.ofPattern(
                                stringResource(dateFormatStringResourceId),
                            ),
                        )
                    BookShelvesType.WANT_TO_READ ->
                        book.addedAt.format(
                            DateTimeFormatter.ofPattern(
                                stringResource(dateFormatStringResourceId),
                            ),
                        )
                },
            maxLines = Dimens.bookListItemShelveTextMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun PagesText(book: Book) {
    Text(
        modifier =
            Modifier
                .padding(end = Dimens.paddingEndSmall),
        text =
            stringResource(
                R.string.book_details__label__book_pages_text,
                book.pageCurrent,
                book.pagesCount,
            ),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.End,
    )
}

@Preview
@Composable
private fun BookListItemFinishedPreview() {
    BookListItem(
        book =
            Book().copy(
                title = "Title",
                author = "Author",
                pageCurrent = 1250,
                pagesCount = 2500,
                finishedAt = LocalDateTime.now(),
                shelf = BookShelvesType.FINISHED,
            ),
        onClickedBook = {},
        holderSize = HolderSize.LARGE,
    )
}

@Preview
@Composable
private fun BookListItemReadingPreview() {
    BookListItem(
        book =
            Book().copy(
                title = "Title",
                author = "Author",
                pageCurrent = 50,
                pagesCount = 250,
                shelf = BookShelvesType.READING,
            ),
        onClickedBook = {},
        holderSize = HolderSize.DEFAULT,
    )
}

@Preview
@Composable
private fun BookListItemWantToReadPreview() {
    BookListItem(
        book =
            Book().copy(
                title = "Title",
                author = "Author",
                pageCurrent = 50,
                pagesCount = 250,
                shelf = BookShelvesType.WANT_TO_READ,
            ),
        onClickedBook = {},
        holderSize = HolderSize.SMALL,
    )
}
