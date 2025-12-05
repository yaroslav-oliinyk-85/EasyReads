package com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.io.File

@Composable
fun BookAddEditCoverImageSection(
    modifier: Modifier = Modifier,
    stateUi: BookAddEditStateUi,
    onCoverClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(Dimens.bookAddEditBookCoverImageSize)
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .border(
                    width = Dimens.buttonBorderWith,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
                ).clickable { onCoverClick() },
        contentAlignment = Alignment.Center,
    ) {
        val context = LocalContext.current
        val bookCoverImageFile: File? =
            if (stateUi.pickedImageName != null) {
                File(context.filesDir, stateUi.pickedImageName)
            } else if (stateUi.book.coverImageFileName != null) {
                File(context.filesDir, stateUi.book.coverImageFileName)
            } else {
                null
            }

        bookCoverImageFile?.let {
            if (it.exists()) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize().alpha(Dimens.bookCoverImageAlpha),
                    model =
                        ImageRequest
                            .Builder(context)
                            .data(bookCoverImageFile)
                            .crossfade(true)
                            .build(),
                    contentDescription =
                        stringResource(
                            R.string.book_cover_image__content_description__text,
                        ),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Text(
            text = stringResource(R.string.book_add_edit__label__change_cover_image_text),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
