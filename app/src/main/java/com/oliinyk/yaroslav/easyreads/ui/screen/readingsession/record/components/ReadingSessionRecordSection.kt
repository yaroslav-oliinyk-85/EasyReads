package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSessionRecordStatusType
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingSessionRecordSection(
    readingSession: ReadingSession,
    notesCount: Int,
    onClickStartPause: () -> Unit,
    onClickShowNotes: () -> Unit,
    onClickAddNote: () -> Unit,
    onClickFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRecording =
        when (readingSession.recordStatus) {
            ReadingSessionRecordStatusType.STARTED -> true
            ReadingSessionRecordStatusType.PAUSED -> false
            ReadingSessionRecordStatusType.FINISHED -> false
        }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingAllSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text =
                    stringResource(
                        R.string.reading_session_record__label__read_time_text,
                        readingSession.readHours,
                        readingSession.readMinutes,
                        readingSession.readSeconds,
                    ),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // Big circular Start/Pause button
            IconButton(
                onClick = onClickStartPause,
                modifier =
                    Modifier
                        .size(Dimens.startReadingSessionButtonSize)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape,
                        ),
            ) {
                Icon(
                    painter =
                        if (isRecording) {
                            painterResource(R.drawable.ic_button_record_pause)
                        } else {
                            painterResource(R.drawable.ic_button_record_start)
                        },
                    contentDescription =
                        if (isRecording) {
                            stringResource(R.string.reading_session_record__label__record_status_started_text)
                        } else {
                            stringResource(R.string.reading_session_record__label__record_status_paused_text)
                        },
                    modifier = Modifier.size(Dimens.startReadingSessionIconSize),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            Text(
                text =
                    if (isRecording) {
                        stringResource(R.string.reading_session_record__label__record_status_started_text)
                    } else {
                        stringResource(R.string.reading_session_record__label__record_status_paused_text)
                    },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.paddingVerticalSmall),
            )

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            Row(
                Modifier.fillMaxWidth(),
            ) {
                // --- See All Notes Button ---
                AppTextButton(
                    onClick = onClickShowNotes,
                    modifier =
                        Modifier
                            .weight(1f),
                ) {
                    Text(
                        text =
                            stringResource(
                                R.string.reading_session_record__button__show_notes_text,
                                notesCount,
                            ),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                Spacer(Modifier.width(Dimens.spacerWidthSmall))

                // --- Add Note Button ---
                AppTextButton(
                    onClick = onClickAddNote,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(R.string.reading_session_record__button__add_note_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spacerHeightSmall))

            // --- Finish Button ---
            AppButton(
                onClick = onClickFinish,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.reading_session_record__button__finish_recording_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionRecordSectionPreview() {
    EasyReadsTheme {
        ReadingSessionRecordSection(
            readingSession = ReadingSession(),
            notesCount = 5,
            onClickStartPause = { },
            onClickShowNotes = { },
            onClickAddNote = { },
            onClickFinish = { },
        )
    }
}
