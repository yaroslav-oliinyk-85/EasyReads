package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.ReadingGoalUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.LocalDate

@Composable
fun ReadingGoalProgressSection(
    uiState: ReadingGoalUiState,
    modifier: Modifier = Modifier,
    onChangeGoalClick: () -> Unit,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val currentYear by remember { mutableIntStateOf(LocalDate.now().year) }

    val animatedProgress: Float by animateFloatAsState(
        targetValue =
            if (uiState.selectedReadingGoal.goal > 0) {
                uiState.finishedBooksCount.toFloat() / uiState.selectedReadingGoal.goal
            } else {
                uiState.selectedReadingGoal.goal.toFloat()
            },
    )

    Card(
        modifier = modifier.fillMaxWidth(),
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
                        uiState.selectedReadingGoal.year,
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
                        uiState.finishedBooksCount,
                        uiState.selectedReadingGoal.goal,
                    ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimens.linearProgressIndicatorHeight),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.background,
            )
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall),
            ) {
                AppIconButton(
                    imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                    contentDescription = "",
                    onClick = {
                        onPrevClick()
                    },
                    modifier = Modifier.rotate(Dimens.rotate180),
                    enabled = uiState.oldestYear < uiState.selectedReadingGoal.year,
                )

                AppTextButton(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(vertical = Dimens.paddingVerticalSmall),
                    enabled = uiState.selectedReadingGoal.year == currentYear,
                    onClick = onChangeGoalClick,
                ) {
                    Text(
                        text = stringResource(R.string.reading_goal__label__goal_change_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                AppIconButton(
                    imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                    contentDescription = "",
                    onClick = {
                        onNextClick()
                    },
                    enabled = uiState.selectedReadingGoal.year < currentYear,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingGoalProgressSectionPreview() {
    EasyReadsTheme {
        ReadingGoalProgressSection(
            uiState =
                ReadingGoalUiState(
                    selectedReadingGoal = ReadingGoal(goal = 12, year = 2025),
                    finishedBooksCount = 6,
                    oldestYear = 2024,
                ),
            onChangeGoalClick = {},
            onPrevClick = {},
            onNextClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingGoalProgressSectionHasPrevYearPreview() {
    EasyReadsTheme {
        ReadingGoalProgressSection(
            uiState =
                ReadingGoalUiState(
                    selectedReadingGoal = ReadingGoal(goal = 6),
                    finishedBooksCount = 3,
                    oldestYear = 2025,
                ),
            onChangeGoalClick = {},
            onPrevClick = {},
            onNextClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingGoalProgressSectionEmptyPreview() {
    EasyReadsTheme {
        ReadingGoalProgressSection(
            uiState = ReadingGoalUiState(),
            onChangeGoalClick = {},
            onPrevClick = {},
            onNextClick = {},
        )
    }
}
