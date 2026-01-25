package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.components.ReadingProgressIndicator
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BookDetailsCoverSection(
    book: Book,
    progressPercentage: Int,
    onClickShelf: (BookShelvesType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingTopMedium,
                        bottom = Dimens.paddingBottomSmall,
                    ).padding(horizontal = Dimens.paddingHorizontalSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BookCoverImage(book = book)

            TitleText(title = book.title)

            AuthorText(author = book.author)

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            DateAndProgressRow(
                book = book,
                progressPercentage = progressPercentage,
            )

            ShelfSelectionButtons(
                book = book,
                onClickShelf = onClickShelf,
            )
        }
    }
}

@Composable
private fun BookCoverImage(
    book: Book,
    modifier: Modifier = Modifier,
) {
    // --- Book Cover ---
    var isBookCoverImageScaledState by rememberSaveable { mutableStateOf(false) }

    val booCoverImageSize =
        if (!isBookCoverImageScaledState) {
            Dimens.bookDetailsBookCoverImageSize
        } else {
            Dimens.bookDetailsBookCoverImageScaledSize
        }

    val context = LocalContext.current
    val bookCoverImageFile: File? =
        if (book.coverImageFileName != null) {
            File(context.filesDir, book.coverImageFileName)
        } else {
            null
        }
    Box(
        modifier =
            modifier
                .size(booCoverImageSize)
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            isBookCoverImageScaledState = !isBookCoverImageScaledState
                        },
                    )
                },
        contentAlignment = Alignment.Center,
    ) {
        bookCoverImageFile?.let { file ->
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(context)
                        .data(file)
                        .crossfade(true)
                        .build(),
                contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                modifier =
                    Modifier
                        .size(booCoverImageSize)
                        .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
private fun TitleText(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.appTitleMediumFontSize),
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.paddingTopSmall,
                    bottom = Dimens.paddingTopTiny,
                ),
    )
}

@Composable
private fun AuthorText(
    author: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = author,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier =
            modifier
                .fillMaxWidth(),
    )
}

@Composable
private fun DateAndProgressRow(
    book: Book,
    progressPercentage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // --- Shelf ---
        Text(
            modifier = Modifier.weight(1f),
            text =
                when (book.shelf) {
                    BookShelvesType.FINISHED ->
                        stringResource(
                            R.string.book_details__label__shelf_finished_text,
                            book.finishedAt?.format(
                                DateTimeFormatter.ofPattern(
                                    stringResource(R.string.date_and_time_format),
                                ),
                            ) ?: "",
                        )
                    BookShelvesType.READING ->
                        stringResource(
                            R.string.book_details__label__shelf_reading_text,
                            book.updatedAt.format(
                                DateTimeFormatter.ofPattern(
                                    stringResource(R.string.date_and_time_format),
                                ),
                            ),
                        )
                    BookShelvesType.WANT_TO_READ ->
                        stringResource(
                            R.string.book_details__label__shelf_want_to_read_text,
                            book.addedAt.format(
                                DateTimeFormatter.ofPattern(
                                    stringResource(R.string.date_and_time_format),
                                ),
                            ),
                        )
                },
            style = MaterialTheme.typography.bodyMedium,
        )

        // --- Progress Indicator ---
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // --- Pages Text ---
            Text(
                text =
                    stringResource(
                        R.string.book_details__label__book_pages_text,
                        book.pageCurrent,
                        book.pagesCount,
                    ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
            )

            Spacer(Modifier.width(Dimens.spacerWidthSmall))

            ReadingProgressIndicator(percentage = progressPercentage)
        }
    }
}

@Composable
private fun ShelfSelectionButtons(
    book: Book,
    onClickShelf: (BookShelvesType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showAllSelvesType by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        // --- FINISHED ---
        AnimatedVisibility(
            visible = showAllSelvesType || BookShelvesType.FINISHED == book.shelf,
        ) {
            AppTextButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopSmall),
                onClick = {
                    onClickShelf(BookShelvesType.FINISHED)
                    showAllSelvesType = !showAllSelvesType
                },
                colors =
                    if (showAllSelvesType && BookShelvesType.FINISHED == book.shelf) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.textButtonColors()
                    },
            ) {
                Text(
                    text =
                        stringResource(
                            R.string.book_details__button__shelf_finished_text,
                        ),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        // --- READING ---
        AnimatedVisibility(
            visible = showAllSelvesType || BookShelvesType.READING == book.shelf,
        ) {
            AppTextButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopSmall),
                onClick = {
                    onClickShelf(BookShelvesType.READING)
                    showAllSelvesType = !showAllSelvesType
                },
                colors =
                    if (showAllSelvesType && BookShelvesType.READING == book.shelf) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.textButtonColors()
                    },
            ) {
                Text(
                    text =
                        stringResource(
                            R.string.book_details__button__shelf_reading_text,
                        ),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        // --- WANT_TO_READ ---
        AnimatedVisibility(
            visible = showAllSelvesType || BookShelvesType.WANT_TO_READ == book.shelf,
        ) {
            AppTextButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopSmall),
                onClick = {
                    onClickShelf(BookShelvesType.WANT_TO_READ)
                    showAllSelvesType = !showAllSelvesType
                },
                colors =
                    if (showAllSelvesType && BookShelvesType.WANT_TO_READ == book.shelf) {
                        ButtonDefaults.buttonColors()
                    } else {
                        ButtonDefaults.textButtonColors()
                    },
            ) {
                Text(
                    text =
                        stringResource(
                            R.string.book_details__button__shelf_want_to_read_text,
                        ),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsCoverSectionFinishedPreview() {
    EasyReadsTheme {
        BookDetailsCoverSection(
            Book().copy(
                title = "Book Title",
                author = "Book Author",
                pagesCount = 250,
                pageCurrent = 50,
                shelf = BookShelvesType.FINISHED,
                isFinished = true,
                finishedAt = LocalDateTime.now(),
            ),
            progressPercentage = 50,
            onClickShelf = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsCoverSectionReadingPreview() {
    EasyReadsTheme {
        BookDetailsCoverSection(
            Book().copy(
                title = "Book Title",
                author = "Book Author",
                pagesCount = 250,
                pageCurrent = 50,
                shelf = BookShelvesType.READING,
            ),
            progressPercentage = 50,
            onClickShelf = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsCoverSectionWantToReadPreview() {
    EasyReadsTheme {
        BookDetailsCoverSection(
            Book().copy(
                title = "Book Title",
                author = "Book Author",
                pagesCount = 250,
                pageCurrent = 50,
                shelf = BookShelvesType.WANT_TO_READ,
            ),
            progressPercentage = 50,
            onClickShelf = { },
        )
    }
}
