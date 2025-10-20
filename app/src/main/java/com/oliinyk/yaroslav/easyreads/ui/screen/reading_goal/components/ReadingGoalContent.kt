package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.presentation.reading_goal.ReadingGoalStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingGoalContent(
    stateUi: ReadingGoalStateUi,
    onChangeGoalClick: () -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            .padding(
                horizontal = Dimens.paddingHorizontalMedium,
                vertical = Dimens.paddingVerticalSmall
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        ReadingGoalProgressSection(
            stateUi = stateUi,
            onChangeGoalClick = onChangeGoalClick
        )
        Spacer(Modifier.height(Dimens.spacerHeightSmall))
        ReadingGoalReadingSummarySection(
            stateUi = stateUi
        )
        Spacer(Modifier.height(Dimens.spacerHeightSmall))
        ReadingGoalFinishedBooksSection(
            books = stateUi.books,
            onBookClick = onBookClick
        )
    }
}