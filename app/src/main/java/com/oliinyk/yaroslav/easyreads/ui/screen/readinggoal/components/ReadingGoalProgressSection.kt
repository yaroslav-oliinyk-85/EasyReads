package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

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
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.ReadingGoalUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.time.LocalDate

@Composable
fun ReadingGoalProgressSection(
    uiState: ReadingGoalUiState,
    modifier: Modifier = Modifier,
    isVisibleChangeGoalButton: Boolean = true,
    onChangeGoalClick: () -> Unit = { /* optional */ },
    onClick: () -> Unit = { /* optional */ },
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
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
            Text(
                text =
                    stringResource(
                        R.string.reading_goal__label__goal_title_text,
                        LocalDate.now().year,
                    ),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            Spacer(Modifier.height(Dimens.spacerHeightMedium))
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
                        uiState.currentYearFinishedBooksCount.toFloat() / uiState.readingGoal.goal
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
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            if (isVisibleChangeGoalButton) {
                AppTextButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.paddingVerticalSmall),
                    onClick = onChangeGoalClick,
                ) {
                    Text(
                        text = stringResource(R.string.reading_goal__label__goal_change_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}
