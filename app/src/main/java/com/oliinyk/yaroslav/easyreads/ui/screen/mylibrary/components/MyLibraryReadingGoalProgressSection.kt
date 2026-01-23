package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.MyLibraryUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.LocalDate

@Composable
fun MyLibraryReadingGoalProgressSection(
    uiState: MyLibraryUiState,
    modifier: Modifier = Modifier,
    isVisibleChangeGoalButton: Boolean = true,
    isEnabledOnClick: Boolean = uiState.readingGoal.goal > 0,
    isGoalSet: Boolean = uiState.readingGoal.goal > 0,
    onChangeGoalClicked: () -> Unit = { /* optional */ },
    onClick: () -> Unit = { /* optional */ },
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(
                    enabled = isEnabledOnClick,
                    onClick = onClick,
                ),
        shape =
            RoundedCornerShape(
                Dimens.roundedCornerShapeSize,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.paddingHorizontalMedium,
                        vertical = Dimens.paddingVerticalSmall,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderTitle()
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            if (isGoalSet) {
                Spacer(Modifier.height(Dimens.spacerHeightSmall))
                ReadingGoalProgressSection(uiState = uiState)
                Spacer(Modifier.height(Dimens.spacerHeightSmall))
            }
            if (isVisibleChangeGoalButton || !isGoalSet) {
                SetChangeGoalButton(
                    isGoalSet = isGoalSet,
                    onChangeGoalClicked = onChangeGoalClicked,
                )
            }
        }
    }
}

@Composable
private fun HeaderTitle() {
    Text(
        text =
            stringResource(
                R.string.reading_goal__label__goal_title_text,
                LocalDate.now().year,
            ),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun ReadingGoalProgressSection(uiState: MyLibraryUiState) {
    Text(
        text =
            stringResource(
                R.string.reading_goal__label__goal_reading_progress_text,
                uiState.currentYearFinishedBooksCount,
                uiState.readingGoal.goal,
            ),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
    Spacer(Modifier.height(Dimens.spacerHeightSmall))
    LinearProgressIndicator(
        progress =
            if (uiState.readingGoal.goal > 0) {
                uiState.currentYearFinishedBooksCount.toFloat() / uiState.readingGoal.goal.toFloat()
            } else {
                uiState.readingGoal.goal.toFloat()
            },
        modifier =
            Modifier
                .fillMaxWidth()
                .height(Dimens.linearProgressIndicatorHeight),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.background,
    )
}

@Composable
private fun SetChangeGoalButton(
    isGoalSet: Boolean,
    onChangeGoalClicked: () -> Unit,
) {
    AppTextButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.paddingVerticalSmall),
        onClick = onChangeGoalClicked,
    ) {
        Text(
            text =
                stringResource(
                    if (!isGoalSet) {
                        R.string.reading_goal__label__goal_set_text
                    } else {
                        R.string.reading_goal__label__goal_change_text
                    },
                ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun MyLibraryReadingGoalProgressSectionPreview() {
    EasyReadsTheme {
        MyLibraryReadingGoalProgressSection(
            uiState =
                MyLibraryUiState(
                    currentYearFinishedBooksCount = 6,
                    readingGoal = ReadingGoal(goal = 12),
                ),
        )
    }
}

@Preview
@Composable
private fun MyLibraryReadingGoalProgressSectionNoChangeGoalButtonPreview() {
    EasyReadsTheme {
        MyLibraryReadingGoalProgressSection(
            uiState =
                MyLibraryUiState(
                    currentYearFinishedBooksCount = 6,
                    readingGoal = ReadingGoal(goal = 12),
                ),
            isVisibleChangeGoalButton = false,
        )
    }
}

@Preview
@Composable
private fun MyLibraryReadingGoalProgressSectionNotSetGoalsPreview() {
    EasyReadsTheme {
        MyLibraryReadingGoalProgressSection(
            uiState = MyLibraryUiState(),
            isVisibleChangeGoalButton = false,
        )
    }
}
