package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelveType
import com.oliinyk.yaroslav.easyreads.presentation.my_library.MyLibraryStateUi
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ShelvesCard(
    modifier: Modifier,
    stateUi: MyLibraryStateUi,
    onShelfClick: (BookShelveType) -> Unit,
    onSeeAllClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.MyLibraryScreen.ShelvesCard.columnPaddingHorizontal,
                    vertical = Dimens.MyLibraryScreen.ShelvesCard.columnPaddingVertical)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.my_library__label__shelves_title_text),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            ShelveItem(
                modifier = modifier,
                label = stringResource(
                    R.string.my_library__label__shelve_finished_text,
                    stateUi.finishedCount
                ),
                onClick = { onShelfClick(BookShelveType.FINISHED) }
            )
            AppDivider()
            ShelveItem(
                modifier = modifier,
                label = stringResource(
                    R.string.my_library__label__shelve_reading_text,
                    stateUi.readingCount
                ),
                onClick = { onShelfClick(BookShelveType.READING) }
            )
            AppDivider()
            ShelveItem(
                modifier = modifier,
                label = stringResource(
                    R.string.my_library__label__shelve_want_to_read_text,
                    stateUi.wantToReadCount
                ),
                onClick = { onShelfClick(BookShelveType.WANT_TO_READ) }
            )
            AppDivider()
            Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
            AppTextButton(
                onClick = onSeeAllClick
            ) {
                Text(
                    text = stringResource(
                        R.string.my_library__label__shelve_see_all_books,
                        stateUi.allCount
                    )
                )
            }
        }
    }
}

@Composable
fun ShelveItem(
    modifier: Modifier,
    label: String,
    onClick: () -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = Dimens.MyLibraryScreen.shelveItemPaddingVertical)
    )
}