package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MILLISECONDS_IN_ONE_SECOND
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MINUTES_IN_ONE_HOUR
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.SECONDS_IN_ONE_MINUTE
import com.oliinyk.yaroslav.easyreads.presentation.book.details.BookDetailsUiState
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.components.ReadingProgressIndicator
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsProgressSection(
    modifier: Modifier = Modifier,
    stateUi: BookDetailsUiState,
    onStartReadingSession: () -> Unit,
    onSeeAll: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingAllSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // --- Read Time ---
                Text(
                    text = stringResource(
                        R.string.book_details__label__book_read_time_text,
                        stateUi.readHours,
                        stateUi.readMinutes
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                // --- Average Read Time Per Hour ---
                Text(
                    text = stringResource(
                        R.string.book_details__label__book_read_average_pages_hour_text,
                        stateUi.readPagesHour
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                // --- Pages ---
                Text(
                    text = stringResource(
                        R.string.book_details__label__book_pages_text,
                        stateUi.book.pageCurrent,
                        stateUi.book.pageAmount
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End
                )

                Spacer(Modifier.width(Dimens.spacerWidthSmall))

                ReadingProgressIndicator(percentage = stateUi.percentage)
            }

            // ---------- Divider ----------
            AppDivider(modifier = Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            if(!stateUi.book.isFinished) {
                // ---------- Start reading session button ----------
                IconButton(
                    onClick = onStartReadingSession,
                    modifier = Modifier
                        .size(Dimens.startReadingSessionButtonSize)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_button_record_start),
                        contentDescription = stringResource(R.string.book_details__label__start_reading_session_text),
                        modifier = Modifier.size(Dimens.startReadingSessionIconSize),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = stringResource(R.string.book_details__label__start_reading_session_text),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.paddingVerticalSmall),
                    textAlign = TextAlign.Center
                )
            }

            // ---------- Footer buttons ----------
            if (stateUi.book.isFinished) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SeeAllReadingSessionButton(
                        quantity = stateUi.readingSessions.size,
                        onSeeAll = onSeeAll,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
