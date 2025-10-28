package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingSessionRecordSection(
    readingSession: ReadingSession,
    onStartPause: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRecording = when(readingSession.recordStatus) {
        ReadingSessionRecordStatusType.STARTED -> true
        ReadingSessionRecordStatusType.PAUSED -> false
        ReadingSessionRecordStatusType.FINISHED -> false
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(Dimens.paddingAllSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.reading_session_record__label__read_time_text,
                    readingSession.readHours,
                    readingSession.readMinutes,
                    readingSession.readSeconds
                ),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // Big circular Start/Pause button
            IconButton(
                onClick = onStartPause,
                modifier = Modifier
                    .size(Dimens.startReadingSessionButtonSize)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = if (isRecording) {
                            painterResource(R.drawable.ic_button_record_pause)
                        } else {
                            painterResource(R.drawable.ic_button_record_start)
                        },
                    contentDescription = if (isRecording) {
                            stringResource(R.string.reading_session_record__label__record_status_started_text)
                        } else {
                            stringResource(R.string.reading_session_record__label__record_status_paused_text)
                        },
                    modifier = Modifier.size(Dimens.startReadingSessionIconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = if (isRecording) {
                        stringResource(R.string.reading_session_record__label__record_status_started_text)
                    } else {
                        stringResource(R.string.reading_session_record__label__record_status_paused_text)
                    },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.paddingVerticalSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordSectionPreview() {
    EasyReadsTheme {
        ReadingSessionRecordSection(
            readingSession = ReadingSession(),
            onStartPause = { }
        )
    }
}