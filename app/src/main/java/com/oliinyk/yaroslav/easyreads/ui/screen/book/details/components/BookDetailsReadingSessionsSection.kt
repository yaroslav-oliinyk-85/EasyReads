package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import android.text.format.DateFormat
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit_dialog.ReadingSessionAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsReadingSessionsSection(
    sessions: List<ReadingSession>,
    isBookFinished: Boolean,
    onStartReadingSession: () -> Unit,
    onSeeAll: () -> Unit,
    onEdit: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingReadingSession: ReadingSession? by rememberSaveable { mutableStateOf(null) }
    editingReadingSession?.let { readingSession ->
        ReadingSessionAddEditDialog(
            readingSession = readingSession,
            onSave = {
                onEdit(it)
                editingReadingSession = null
            },
            onDismissRequest = { editingReadingSession = null }
        )
    }

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
            if (sessions.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(R.string.book_details__label__no_reading_sessions_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                ShowLatestReadingSessionInfoRow(
                    latestReadingSession = sessions.first(),
                    onClickEditReadingSession = { latestReadingSession ->
                        editingReadingSession = latestReadingSession
                    }
                )
            }

            if(!isBookFinished) {
                AppDivider(modifier = Modifier.padding(vertical = Dimens.paddingVerticalSmall))

                StartReadingSessionButton(onStartReadingSession)
            }

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            SeeAllReadingSessionButton(
                quantity = sessions.size,
                onSeeAll = onSeeAll,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ShowLatestReadingSessionInfoRow(
    latestReadingSession: ReadingSession,
    onClickEditReadingSession: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // --- reading summery row ---
            Row {
                // --- read time ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_time_text,
                        latestReadingSession.readHours,
                        latestReadingSession.readMinutes
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                // --- read pages hour ---
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(
                        R.string.book_details__label__book_read_pages_hour_text,
                        latestReadingSession.readPagesHour
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                // --- read pages ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_pages_text,
                        latestReadingSession.readPages
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
            }

            // --- secondary row (date + page range) ---
            Row(
                modifier = Modifier.padding(top = Dimens.paddingTopTiny),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // --- started date + time ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__date_text,
                        DateFormat.format(
                            stringResource(R.string.date_and_time_format),
                            latestReadingSession.startedDate
                        ).toString()
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                // --- start/end page ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_end_page_text,
                        latestReadingSession.startPage,
                        latestReadingSession.endPage
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(Modifier.width(Dimens.spacerWidthSmall))
        // --- edit reading session button ---
        AppIconButton(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.menu_item__edit_text),
            onClick = { onClickEditReadingSession(latestReadingSession) }
        )
    }
}

@Composable
private fun StartReadingSessionButton(
    onStartReadingSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .padding(top = Dimens.paddingTopSmall),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SeeAllReadingSessionButton(
    quantity: Int,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextButton(
        onClick = onSeeAll,
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                R.string.book_details__button__see_all_reading_sessions_text,
                quantity
            ),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionReadingPreview() {
    BookDetailsReadingSessionsSection(
        sessions = listOf(ReadingSession()),
        isBookFinished = false,
        onStartReadingSession = {},
        onSeeAll = {},
        onEdit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionReadingEmptyPreview() {
    BookDetailsReadingSessionsSection(
        sessions = emptyList(),
        isBookFinished = false,
        onStartReadingSession = {},
        onSeeAll = {},
        onEdit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionFinishedPreview() {
    BookDetailsReadingSessionsSection(
        sessions = listOf(ReadingSession()),
        isBookFinished = true,
        onStartReadingSession = {},
        onSeeAll = {},
        onEdit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionFinishedEmptyPreview() {
    BookDetailsReadingSessionsSection(
        sessions = emptyList(),
        isBookFinished = true,
        onStartReadingSession = {},
        onSeeAll = {},
        onEdit = {}
    )
}
