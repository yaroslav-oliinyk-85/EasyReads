package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.addeditdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.extension.takeFirstTwoDigits
import com.oliinyk.yaroslav.easyreads.domain.extension.toBookPage
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppDatePickerDialog
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReadingSessionAddEditDialog(
    readingSession: ReadingSession,
    pagesCount: Int,
    onSave: (ReadingSession) -> Unit,
    onDismissRequest: () -> Unit,
    isRemoveButtonEnabled: Boolean = false,
    onRemove: (ReadingSession) -> Unit = { },
) {
    // --- State ---
    var uiStateDialog by rememberSaveable {
        mutableStateOf(
            ReadingSessionAddEditDialogUiState(
                startPage = readingSession.startPage,
                endPage = readingSession.endPage,
                readPages = readingSession.readPages,
                hours = readingSession.readHours,
                minutes = readingSession.readMinutes,
                seconds = readingSession.readSeconds,
                startedAt = readingSession.startedAt,
            ),
        )
    }

    var showStartedDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState =
        rememberDatePickerState(
            selectableDates =
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                        utcTimeMillis <= System.currentTimeMillis()
                },
        )

    if (showStartedDatePickerDialog) {
        AppDatePickerDialog(
            datePickerState = datePickerState,
            onDateSelected = { selectedDate ->
                uiStateDialog =
                    uiStateDialog
                        .copy(startedAt = LocalDateTime.of(selectedDate, uiStateDialog.startedAt.toLocalTime()))
            },
            onDismissRequest = { showStartedDatePickerDialog = false },
        )
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        // --- Error Messages ---
        val endPageInputErrorMessageText =
            stringResource(
                R.string.reading_session_add_edit_dialog__error__end_page_message_text,
            )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(Dimens.paddingAllMedium),
            ) {
                StartedDateEditField(
                    uiStateDialog = uiStateDialog,
                    onStartedDateClick = { showStartedDatePickerDialog = true },
                )

                // --- Hours/Minutes/Seconds ---
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.paddingTopMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    HoursEditField(
                        uiStateDialog = uiStateDialog,
                        onValueChange = { value ->
                            uiStateDialog = uiStateDialog.copy(hours = value.takeFirstTwoDigits())
                        },
                        Modifier.weight(1f),
                    )

                    Spacer(Modifier.width(Dimens.spacerWidthSmall))

                    MinutesEditField(
                        uiStateDialog = uiStateDialog,
                        onValueChange = { value ->
                            uiStateDialog =
                                uiStateDialog.copy(
                                    minutes =
                                        value
                                            .takeFirstTwoDigits()
                                            .coerceIn(AppConstants.MINUTES_ALLOWED_RANGE),
                                )
                        },
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(Modifier.width(Dimens.spacerWidthSmall))

                    SecondsEditField(
                        uiStateDialog = uiStateDialog,
                        onValueChange = { value ->
                            uiStateDialog =
                                uiStateDialog.copy(
                                    seconds =
                                        value
                                            .takeFirstTwoDigits()
                                            .coerceIn(AppConstants.SECONDS_ALLOWED_RANGE),
                                )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }

                StartPageEditField(
                    modifier = Modifier.padding(top = Dimens.paddingTopMedium),
                    uiStateDialog = uiStateDialog,
                    onValueChange = { value ->
                        val startPageValue = value.toBookPage()
                        uiStateDialog = uiStateDialog.copy(startPage = startPageValue)
                    },
                )
                EndPageEditField(
                    modifier = Modifier.padding(top = Dimens.paddingTopMedium),
                    uiStateDialog = uiStateDialog,
                    onValueChange = { value ->
                        val endPageValue = value.toBookPage().coerceIn(0..pagesCount)
                        uiStateDialog =
                            uiStateDialog.copy(
                                endPage = endPageValue,
                                readPages = endPageValue - uiStateDialog.startPage,
                                endPageInputErrorMessage =
                                    if (endPageValue <= uiStateDialog.startPage) {
                                        endPageInputErrorMessageText
                                    } else {
                                        ""
                                    },
                            )
                    },
                )

                AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalMedium))

                SaveButton(
                    onClick = {
                        if (uiStateDialog.endPage <= uiStateDialog.startPage) {
                            uiStateDialog =
                                uiStateDialog.copy(
                                    endPageInputErrorMessage = endPageInputErrorMessageText,
                                )
                        } else {
                            uiStateDialog = uiStateDialog.copy(endPageInputErrorMessage = "")
                            onSave(
                                readingSession.copy(
                                    startedAt = uiStateDialog.startedAt,
                                    startPage = uiStateDialog.startPage,
                                    endPage = uiStateDialog.endPage,
                                    readPages = uiStateDialog.readPages,
                                    readTimeInMilliseconds =
                                        ReadingSession
                                            .toReadTimeInMilliseconds(
                                                uiStateDialog.hours,
                                                uiStateDialog.minutes,
                                                uiStateDialog.seconds,
                                            ),
                                ),
                            )
                        }
                    },
                )

                if (isRemoveButtonEnabled) {
                    Spacer(Modifier.height(Dimens.spacerHeightSmall))

                    RemoveButton(onClick = { onRemove(readingSession) })
                }

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                // --- Buttons Cancel ---
                CancelButton(onClick = onDismissRequest)
            }
        }
    }
}

@Composable
private fun StartPageEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label =
            stringResource(
                R.string.reading_session_add_edit_dialog__label__start_page_text,
            ),
        labelError = uiStateDialog.startPageInputErrorMessage,
        value = uiStateDialog.startPage.toString(),
        onValueChange = onValueChange,
        hint =
            stringResource(
                R.string.reading_session_add_edit_dialog__hint__enter_start_page_text,
            ),
        readOnly = uiStateDialog.startPageReadOnly,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun EndPageEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label =
            stringResource(
                R.string.reading_session_add_edit_dialog__label__end_page_text,
            ),
        labelError = uiStateDialog.endPageInputErrorMessage,
        value = if (uiStateDialog.endPage == 0) "" else uiStateDialog.endPage.toString(),
        onValueChange = onValueChange,
        hint =
            stringResource(
                R.string.reading_session_add_edit_dialog__hint__enter_end_page_text,
            ),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun HoursEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label =
            stringResource(
                R.string.reading_session_add_edit_dialog__label__read_hour_text,
            ),
        labelTextAlign = TextAlign.Center,
        hint = "0",
        value = if (uiStateDialog.hours == 0) "" else uiStateDialog.hours.toString(),
        onValueChange = onValueChange,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        modifier = modifier,
    )
}

@Composable
private fun MinutesEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label =
            stringResource(
                R.string.reading_session_add_edit_dialog__label__read_minutes_text,
            ),
        labelTextAlign = TextAlign.Center,
        hint = "0",
        value = if (uiStateDialog.minutes == 0) "" else uiStateDialog.minutes.toString(),
        onValueChange = onValueChange,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        modifier = modifier,
    )
}

@Composable
private fun SecondsEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label =
            stringResource(
                R.string.reading_session_add_edit_dialog__label__read_seconds_text,
            ),
        labelTextAlign = TextAlign.Center,
        hint = "0",
        value = if (uiStateDialog.seconds == 0) "" else uiStateDialog.seconds.toString(),
        onValueChange = onValueChange,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        modifier = modifier,
    )
}

@Composable
private fun StartedDateEditField(
    uiStateDialog: ReadingSessionAddEditDialogUiState,
    onStartedDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppEditField(
        label = stringResource(R.string.reading_session_add_edit_dialog__label__started_date_text),
        value =
            uiStateDialog.startedAt.format(
                DateTimeFormatter.ofPattern(
                    stringResource(R.string.date_format),
                ),
            ),
        hint = "",
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            AppIconButton(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                enabledBorder = false,
                onClick = onStartedDateClick,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun SaveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text =
                stringResource(
                    R.string.dialog__button__save_text,
                ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun RemoveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors =
            ButtonDefaults.textButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
    ) {
        Text(
            text =
                stringResource(
                    R.string.dialog__button__remove_text,
                ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun CancelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppTextButton(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        text = stringResource(R.string.dialog__button__cancel_text),
    )
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionAddEditDialogPreview() {
    EasyReadsTheme {
        ReadingSessionAddEditDialog(
            readingSession = ReadingSession(startPage = 25),
            pagesCount = 250,
            onSave = { },
            onDismissRequest = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionAddEditDialogWithRemoveButtonPreview() {
    EasyReadsTheme {
        ReadingSessionAddEditDialog(
            readingSession = ReadingSession(startPage = 25),
            pagesCount = 250,
            isRemoveButtonEnabled = true,
            onSave = { },
            onDismissRequest = { },
        )
    }
}
