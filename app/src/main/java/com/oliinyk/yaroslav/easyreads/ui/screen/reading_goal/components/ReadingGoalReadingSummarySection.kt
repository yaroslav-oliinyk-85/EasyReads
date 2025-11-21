package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.ReadingGoalStateUi
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingGoalReadingSummarySection(
    stateUi: ReadingGoalStateUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall
                )
        ) {
            Text(
                text = stringResource(R.string.reading_goal__label__summery_title_text),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppDivider()
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(
                        R.string.reading_goal__label__summery_read_time_text,
                        stateUi.readHours,
                        stateUi.readMinutes
                    ),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        R.string.reading_goal__label__summery_read_average_pages_hour_text,
                        stateUi.averagePagesHour
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(
                        R.string.reading_goal__label__summery_read_pages_text,
                        stateUi.readPages
                    ),
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
