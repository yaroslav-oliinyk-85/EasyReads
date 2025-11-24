package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.components.AppBadge
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.MyLibraryUiState
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun MyLibraryShelvesSection(
    uiState: MyLibraryUiState,
    onShelfClick: (BookShelvesType) -> Unit,
    onSeeAllClick: () -> Unit,
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
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.my_library__label__shelves_title_text),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            MyLibraryShelvesItem(
                label = stringResource(
                    R.string.my_library__label__shelf_finished_text
                ),
                itemCount = uiState.finishedCount.toString(),
                onClick = { onShelfClick(BookShelvesType.FINISHED) }
            )
            AppDivider()
            MyLibraryShelvesItem(
                label = stringResource(
                    R.string.my_library__label__shelf_reading_text
                ),
                itemCount = uiState.readingCount.toString(),
                onClick = { onShelfClick(BookShelvesType.READING) }
            )
            AppDivider()
            MyLibraryShelvesItem(
                label = stringResource(
                    R.string.my_library__label__shelf_want_to_read_text
                ),
                itemCount = uiState.wantToReadCount.toString(),
                onClick = { onShelfClick(BookShelvesType.WANT_TO_READ) }
            )
            AppDivider()
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppTextButton(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = Dimens.paddingVerticalSmall),
                onClick = onSeeAllClick
            ) {
                Text(
                    text = stringResource(
                        R.string.my_library__label__shelf_see_all_books
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.width(Dimens.spacerWidthSmall))
                AppBadge(
                    text = uiState.allCount.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyLibraryShelvesSectionPreview() {
    EasyReadsTheme {
        MyLibraryShelvesSection(
            uiState = MyLibraryUiState(),
            onShelfClick = { },
            onSeeAllClick = {}
        )
    }
}
