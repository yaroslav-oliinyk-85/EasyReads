package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.oliinyk.yaroslav.easyreads.ui.components.AppConfirmDialog
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsUiState
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.addeditdialog.ReadingSessionAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.format.DateTimeFormatter

@Composable
fun BookDetailsReadingSessionsSection(
    uiState: BookDetailsUiState,
    isReading: Boolean,
    onStartReadingSession: () -> Unit,
    onEditClicked: (ReadingSession) -> Unit,
    onRemoveClicked: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier,
) {
    var editingReadingSession: ReadingSession? by rememberSaveable { mutableStateOf(null) }
    var removeReadingSessionState: ReadingSession? by rememberSaveable { mutableStateOf(null) }

    // ----- Layout -----

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isReading) {
            StartReadingSessionButton(onStartReadingSession)

            Spacer(Modifier.height(Dimens.spacerHeightSmall))
        }

        ReadingStatisticInfoRow(uiState = uiState)

        Spacer(Modifier.height(Dimens.spacerHeightSmall))

        ReadingSessionList(
            readingSessions = uiState.readingSessions,
            onClickedEdit = {
                editingReadingSession = it
            },
        )
    }

    // ----- Dialogs -----

    editingReadingSession?.let { readingSession ->
        ReadingSessionAddEditDialog(
            readingSession = readingSession,
            pagesCount = uiState.book.pagesCount,
            isRemoveButtonEnabled = true,
            onSave = {
                onEditClicked(it)
                editingReadingSession = null
            },
            onRemove = {
                editingReadingSession = null
                removeReadingSessionState = it
            },
            onDismissRequest = { editingReadingSession = null },
        )
    }

    removeReadingSessionState?.let { readingSession ->
        AppConfirmDialog(
            title =
                stringResource(
                    R.string.reading_session_list__confirmation_dialog__title_text,
                ),
            message =
                stringResource(
                    R.string.reading_session_list__confirmation_dialog__message_text,
                    readingSession.startedAt.format(
                        DateTimeFormatter.ofPattern(
                            stringResource(R.string.date_and_time_format),
                        ),
                    ),
                    readingSession.readPages,
                    readingSession.startPage,
                    readingSession.endPage,
                ),
            onConfirm = {
                onRemoveClicked(readingSession)
                removeReadingSessionState = null
            },
            onDismiss = { removeReadingSessionState = null },
        )
    }
}

@Composable
private fun ReadingSessionList(
    readingSessions: List<ReadingSession>,
    onClickedEdit: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
    ) {
        readingSessions.forEach { readingSession ->
            ReadingSessionListItem(
                readingSession = readingSession,
                onClickedEdit = onClickedEdit,
            )
        }
    }
}

@Composable
private fun ReadingSessionListItem(
    readingSession: ReadingSession,
    onClickedEdit: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    onClickedEdit(readingSession)
                },
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingAllSmall),
            verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
        ) {
            Row {
                // --- read date ---
                Text(
                    text =
                        stringResource(
                            R.string.reading_session_list_item__label__date_text,
                            readingSession.startedAt.format(
                                DateTimeFormatter.ofPattern(
                                    stringResource(R.string.date_and_time_format),
                                ),
                            ),
                        ),
                    style = MaterialTheme.typography.labelLarge,
                )
                // --- read pages ---
                Text(
                    modifier = Modifier.weight(1f),
                    text =
                        stringResource(
                            R.string.reading_session_list_item__label__read_pages_text,
                            readingSession.readPages,
                        ),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End,
                )
            }
            Row {
                // --- read time ---
                Text(
                    text =
                        if (readingSession.readHours > 0) {
                            stringResource(
                                R.string.reading_session_list_item__label__read_time_hour_minutes_text,
                                readingSession.readHours,
                                readingSession.readMinutes,
                            )
                        } else {
                            stringResource(
                                R.string.reading_session_list_item__label__read_time_minutes_text,
                                readingSession.readMinutes,
                            )
                        },
                    style = MaterialTheme.typography.bodySmall,
                )
                // --- read pages hour ---
                Text(
                    modifier = Modifier.weight(1f),
                    text =
                        stringResource(
                            R.string.book_details__label__book_read_pages_hour_text,
                            readingSession.readPagesHour,
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Composable
private fun ReadingStatisticInfoRow(
    uiState: BookDetailsUiState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingAllSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // --- Total Read Time ---
            Text(
                modifier = Modifier.weight(1f),
                text =
                    stringResource(
                        R.string.book_details__label__book_total_read_time_text,
                        uiState.readHours,
                        uiState.readMinutes,
                    ),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            // --- Average Read Time Per Hour ---
            Text(
                modifier = Modifier.weight(1f),
                text =
                    stringResource(
                        R.string.book_details__label__book_read_average_pages_hour_text,
                        uiState.readPagesHour,
                    ),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun StartReadingSessionButton(
    onStartReadingSession: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onStartReadingSession,
        modifier =
            modifier
                .size(Dimens.startReadingSessionButtonSize)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_button_record_start),
            contentDescription = stringResource(R.string.book_details__label__start_reading_session_text),
            modifier = Modifier.size(Dimens.startReadingSessionIconSize),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionReadingPreview() {
    EasyReadsTheme {
        Column {
            BookDetailsReadingSessionsSection(
                uiState =
                    BookDetailsUiState(
                        readingSessions = listOf(ReadingSession(), ReadingSession(), ReadingSession()),
                    ),
                isReading = true,
                onStartReadingSession = {},
                onEditClicked = {},
                onRemoveClicked = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionReadingEmptyPreview() {
    EasyReadsTheme {
        Column {
            BookDetailsReadingSessionsSection(
                uiState = BookDetailsUiState(),
                isReading = true,
                onStartReadingSession = {},
                onEditClicked = {},
                onRemoveClicked = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionFinishedPreview() {
    EasyReadsTheme {
        Column {
            BookDetailsReadingSessionsSection(
                uiState =
                    BookDetailsUiState(
                        readingSessions =
                            listOf(
                                ReadingSession(readTimeInMilliseconds = 3000000),
                                ReadingSession(readTimeInMilliseconds = 3600000),
                                ReadingSession(readTimeInMilliseconds = 4800000),
                            ),
                    ),
                isReading = false,
                onStartReadingSession = {},
                onEditClicked = {},
                onRemoveClicked = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsReadingSessionsSectionFinishedEmptyPreview() {
    EasyReadsTheme {
        Column {
            BookDetailsReadingSessionsSection(
                uiState = BookDetailsUiState(),
                isReading = false,
                onStartReadingSession = {},
                onEditClicked = {},
                onRemoveClicked = {},
            )
        }
    }
}
