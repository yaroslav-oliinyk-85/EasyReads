package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelveType
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File

@Composable
fun BookDetailsCoverSection(
    book: Book,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.paddingTopMedium,
                    bottom = Dimens.paddingBottomSmall
                )
                .padding(horizontal = Dimens.paddingHorizontalSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Book Cover ---
            var isBookCoverImageScaledState by rememberSaveable { mutableStateOf(false) }

            val booCoverImageSize = if (!isBookCoverImageScaledState) {
                Dimens.bookDetailsBookCoverImageSize
            } else {
                Dimens.bookDetailsBookCoverImageScaledSize
            }

            val context = LocalContext.current
            val bookCoverImageFile: File? = if (book.coverImageFileName != null) {
                File(context.filesDir, book.coverImageFileName)
            } else {
                null
            }
            Box(
                modifier = Modifier
                    .size(booCoverImageSize)
                    .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(
                        onClick = {
                            isBookCoverImageScaledState = !isBookCoverImageScaledState
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                bookCoverImageFile?.let { file ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(file)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                        modifier = Modifier
                            .size(booCoverImageSize)
                            .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            // --- Title ---
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.appTitleMediumFontSize),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.paddingTopSmall,
                        bottom = Dimens.paddingTopTiny
                    )
            )
            AppDivider()
            // --- Author ---
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.paddingVerticalTiny)
            )
            // --- Shelf Label ---
            Text(
                text = when (book.shelve) {
                    BookShelveType.WANT_TO_READ -> stringResource(R.string.book_details__label__shelve_want_to_read_text)
                    BookShelveType.READING -> stringResource(R.string.book_details__label__shelve_reading_text)
                    BookShelveType.FINISHED -> stringResource(R.string.book_details__label__shelve_finished_text)
                },
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsCoverSectionPreview() {
    BookDetailsCoverSection(
        Book().copy(
            title = "Book Title",
            author = "Book Author"
        )
    )
}