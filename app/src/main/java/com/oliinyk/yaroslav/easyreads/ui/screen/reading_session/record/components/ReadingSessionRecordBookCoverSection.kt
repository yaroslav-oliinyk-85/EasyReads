package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.io.File

@Composable
fun ReadingSessionRecordBookCoverSection(
    book: Book,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingAllSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val bookCoverImageFile: File? = if (book.coverImageFileName != null) {
                File(context.filesDir, book.coverImageFileName)
            } else {
                null
            }
            if (bookCoverImageFile != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(bookCoverImageFile)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                    modifier = Modifier
                        .padding(vertical = Dimens.paddingVerticalSmall)
                        .size(Dimens.readingSessionRecordBookCoverImageSize)
                        .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(android.R.drawable.ic_menu_gallery),
                    contentDescription = stringResource(R.string.book_cover_image__content_description__text),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(vertical = Dimens.paddingVerticalSmall)
                        .size(Dimens.readingSessionRecordBookCoverImageSize)
                        .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize)),
                )
            }

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = Dimens.appTitleMediumFontSize),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalTiny))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                // --- Pages ---
                Text(
                    text = stringResource(
                        R.string.reading_session_record__label__read_pages_text,
                        book.pageCurrent,
                        book.pageAmount
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordBookCoverSectionPreview() {
    EasyReadsTheme {
        ReadingSessionRecordBookCoverSection(
            Book().copy(
                title = "Book title",
                author = "Author",
                pageCurrent = 50,
                pageAmount = 250
            )
        )
    }
}