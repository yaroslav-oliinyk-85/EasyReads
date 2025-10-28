package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
            val context = LocalContext.current
            val bookCoverImageFile: File? = if (book.coverImageFileName != null) {
                File(context.filesDir, book.coverImageFileName)
            } else {
                null
            }
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(bookCoverImageFile)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                modifier = Modifier
                    .size(Dimens.bookDetailsBookCoverImageSize)
                    .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize)),
                contentScale = ContentScale.Crop
            )
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