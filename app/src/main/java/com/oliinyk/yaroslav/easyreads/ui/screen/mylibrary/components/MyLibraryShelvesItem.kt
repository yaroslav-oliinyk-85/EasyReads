package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.io.File

@Composable
fun MyLibraryShelvesItem(
    label: String,
    book: Book?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(Dimens.paddingAllTiny)
                    .size(Dimens.myLibraryBookCoverImageSize)
                    .clip(RoundedCornerShape(Dimens.roundedCornerShapeSizeTiny)),
            contentAlignment = Alignment.Center,
        ) {
            val context = LocalContext.current
            book?.coverImageFileName?.let { coverImageFileName ->
                val file = File(context.filesDir, coverImageFileName)
                if (file.isFile && file.exists()) {
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
                }
            }
        }
        Spacer(Modifier.width(Dimens.spacerWidthSmall))
        Text(
            text = label,
            fontSize = Dimens.appTitleMediumFontSize,
            style = MaterialTheme.typography.titleMedium,
            modifier =
                modifier
                    .padding(
                        vertical = Dimens.paddingVerticalMedium,
                        horizontal = Dimens.paddingHorizontalSmall,
                    ),
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = label,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyLibraryShelvesItemPreview() {
    EasyReadsTheme {
        MyLibraryShelvesItem(
            label = "Finished 24",
            book = Book(),
            onClick = { },
        )
    }
}
