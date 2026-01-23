package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

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
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.ReadingGoalUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingGoalContent(
    uiState: ReadingGoalUiState,
    onChangeGoalClick: () -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall,
                ).background(MaterialTheme.colorScheme.background),
    ) {
        ReadingGoalProgressSection(
            uiState = uiState,
            onChangeGoalClick = onChangeGoalClick,
            onPrevClick = onPrevClick,
            onNextClick = onNextClick,
        )
        Spacer(Modifier.height(Dimens.spacerHeightSmall))
        ReadingGoalSummarySection(
            uiState = uiState,
        )
        Spacer(Modifier.height(Dimens.spacerHeightSmall))
        ReadingGoalFinishedBooksSection(
            books = uiState.finishedBooks,
            onBookClick = onBookClick,
        )
    }
}
