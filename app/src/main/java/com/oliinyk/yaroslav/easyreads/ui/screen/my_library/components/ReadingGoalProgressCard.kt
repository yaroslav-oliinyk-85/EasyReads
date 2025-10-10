package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

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
import com.oliinyk.yaroslav.easyreads.presentation.my_library.MyLibraryStateUi
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReadingGoalProgressCard(
    modifier: Modifier,
    stateUi: MyLibraryStateUi,
    isVisibleChangeGoalButton: Boolean = true,
    onChangeGoalClick: () -> Unit = { /* optional */ },
    onClick: () -> Unit = { /* optional */ }
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(
            Dimens.roundedCornerShapeSize
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.MyLibraryScreen.ReadingGoalCard.columnPaddingHorizontal,
                    vertical = Dimens.MyLibraryScreen.ReadingGoalCard.columnPaddingVertical
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.reading_goal__label__goal_title_text,
                    SimpleDateFormat(
                        stringResource(R.string.date_year_format),
                        Locale.getDefault()
                    ).format(Date())
                ),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
            AppDivider(modifier = modifier)
            Spacer(modifier = modifier.height(Dimens.spacerHeightMedium))
            Text(
                text = stringResource(
                    R.string.reading_goal__label__goal_reading_progress_text,
                    stateUi.currentYearFinishedBooksCount,
                    stateUi.readingGoals
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
            LinearProgressIndicator(
                progress = if (stateUi.readingGoals > 0) {
                    stateUi.currentYearFinishedBooksCount.toFloat() / stateUi.readingGoals
                } else {
                    stateUi.readingGoals.toFloat()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(Dimens.MyLibraryScreen.ReadingGoalCard.linearProgressIndicatorHeight),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = modifier.height(Dimens.spacerHeightSmall))
            if (isVisibleChangeGoalButton) {
                AppTextButton(
                    onClick = onChangeGoalClick
                ) {
                    Text(text = stringResource(R.string.reading_goal__label__goal_change_text))
                }
            }
        }
    }
}