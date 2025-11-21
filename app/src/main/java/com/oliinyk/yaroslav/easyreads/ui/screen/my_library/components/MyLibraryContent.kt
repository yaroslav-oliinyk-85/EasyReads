package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.MyLibraryStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun MyLibraryContent(
    stateUi: MyLibraryStateUi,
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
                stateUi = stateUi,
                isVisibleChangeGoalButton = false,
                onClick = onReadingGoalClick
            )
        }
        item {
            MyLibraryShelvesSection(
                stateUi = stateUi,
                onShelfClick = onShelfClick,
                onSeeAllClick = onSeeAllClick
            )
        }
    }
}