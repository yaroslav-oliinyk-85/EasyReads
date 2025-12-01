package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.MyLibraryUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun MyLibraryContent(
    uiState: MyLibraryUiState,
    onReadingGoalClick: () -> Unit,
    onShelfClick: (BookShelvesType) -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = Dimens.paddingHorizontalMedium,
                vertical = Dimens.paddingVerticalSmall),
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
    ) {
        item {
            MyLibraryReadingGoalProgressSection(
                uiState = uiState,
                isVisibleChangeGoalButton = false,
                onClick = onReadingGoalClick
            )
        }
        item {
            MyLibraryShelvesSection(
                uiState = uiState,
                onShelfClick = onShelfClick,
                onSeeAllClick = onSeeAllClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyLibraryContentPreView() {
    EasyReadsTheme {
        MyLibraryContent(
            uiState = MyLibraryUiState(),
            onReadingGoalClick = {},
            onShelfClick = {},
            onSeeAllClick = {}
        )
    }
}
