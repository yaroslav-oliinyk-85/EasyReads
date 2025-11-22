package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit_dialog

import android.os.Parcelable
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReadingSessionAddEditUiStateDialog(
    val startPage: Int = 0,
    val endPage: Int = 0,
    val readPages: Int = 0,

    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,

    val startPageReadOnly: Boolean = true,

    val startPageInputErrorMessage: String = "",
    val endPageInputErrorMessage: String = ""
) : Parcelable

@Composable
fun ReadingSessionAddEditDialog(
    readingSession: ReadingSession,
    onSave: (ReadingSession) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        // --- Error Messages ---
        val startPageInputErrorMessageText = stringResource(
            R.string.reading_session_add_edit_dialog__error__start_page_message_text
        )
        val endPageInputErrorMessageText = stringResource(
            R.string.reading_session_add_edit_dialog__error__end_page_message_text
        )

        // --- State ---
        var uiStateDialog by rememberSaveable {
            mutableStateOf(
                ReadingSessionAddEditUiStateDialog(
                    startPage = readingSession.startPage,
                    endPage = readingSession.endPage,
                    readPages = readingSession.readPages,
                    hours = readingSession.readHours,
                    minutes = readingSession.readMinutes,
                    seconds = readingSession.readSeconds
                )
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(Dimens.paddingAllMedium)
            ) {
                // --- Start Page ---
                AppEditField(
                    label = stringResource(
                        R.string.reading_session_add_edit_dialog__label__start_page_text
                    ),
                    labelError = uiStateDialog.startPageInputErrorMessage,
                    value = uiStateDialog.startPage.toString(),
                    onValueChange = { value ->
                        val startPageValue = value.toBookPage()
                        uiStateDialog = uiStateDialog.copy(startPage =  startPageValue)
                    },
                    hint = stringResource(
                        R.string.reading_session_add_edit_dialog__hint__enter_start_page_text
                    ),
                    readOnly = uiStateDialog.startPageReadOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                // --- End Page ---
                AppEditField(
                    label = stringResource(
                        R.string.reading_session_add_edit_dialog__label__end_page_text
                    ),
                    labelError = uiStateDialog.endPageInputErrorMessage,
                    value = if(uiStateDialog.endPage == 0) "" else uiStateDialog.endPage.toString(),
                    onValueChange = { value ->
                        val endPageValue = value.toBookPage()
                        uiStateDialog = uiStateDialog.copy(
                            endPage = endPageValue,
                            readPages = endPageValue - uiStateDialog.startPage,
                            endPageInputErrorMessage = if (endPageValue <= uiStateDialog.startPage) {
                                    endPageInputErrorMessageText
                                } else {
                                    ""
                                }
                        )
                    },
                    hint = stringResource(
                        R.string.reading_session_add_edit_dialog__hint__enter_end_page_text
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopMedium),
                )

                // --- Hours/Minutes/Seconds ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopMedium),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // --- Hours ---
                    AppEditField(
                        label = stringResource(
                            R.string.reading_session_add_edit_dialog__label__read_hour_text
                        ),
                        labelTextAlign = TextAlign.Center,
                        hint = "0",
                        value = if (uiStateDialog.hours == 0) "" else uiStateDialog.hours.toString(),
                        onValueChange = { value ->
                            uiStateDialog = uiStateDialog.copy(hours = value.takeFirstTwoDigits())
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(Dimens.spacerWidthSmall))

                    // --- Minutes ---
                    AppEditField(
                        label = stringResource(
                            R.string.reading_session_add_edit_dialog__label__read_minutes_text
                        ),
                        labelTextAlign = TextAlign.Center,
                        hint = "0",
                        value = if (uiStateDialog.minutes == 0) "" else uiStateDialog.minutes.toString(),
                        onValueChange = { value ->
                            uiStateDialog = uiStateDialog.copy(
                                minutes = value.takeFirstTwoDigits()
                                    .coerceIn(AppConstants.MINUTES_ALLOWED_RANGE)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(Dimens.spacerWidthSmall))

                    // --- Seconds ---
                    AppEditField(
                        label = stringResource(
                            R.string.reading_session_add_edit_dialog__label__read_seconds_text
                        ),
                        labelTextAlign = TextAlign.Center,
                        hint = "0",
                        value = if (uiStateDialog.seconds == 0) "" else uiStateDialog.seconds.toString(),
                        onValueChange = { value ->
                            uiStateDialog = uiStateDialog.copy(
                                seconds = value.takeFirstTwoDigits()
                                    .coerceIn(AppConstants.SECONDS_ALLOWED_RANGE)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalMedium))

                // --- Buttons Save ---
                AppButton(
                    onClick = {
                        if (uiStateDialog.endPage <= uiStateDialog.startPage) {
                            uiStateDialog = uiStateDialog.copy(
                                endPageInputErrorMessage = endPageInputErrorMessageText
                            )
                        } else {
                            uiStateDialog = uiStateDialog.copy(endPageInputErrorMessage = "")
                            onSave(
                                readingSession.copy(
                                    startPage = uiStateDialog.startPage,
                                    endPage = uiStateDialog.endPage,
                                    readPages = uiStateDialog.readPages,
                                    readTimeInMilliseconds = ReadingSession
                                        .toReadTimeInMilliseconds(
                                            uiStateDialog.hours,
                                            uiStateDialog.minutes,
                                            uiStateDialog.seconds
                                        )
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(
                            R.string.dialog__button__save_text
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                // --- Buttons Cancel ---
                AppTextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(
                            R.string.dialog__button__cancel_text
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingSessionAddEditDialogPreview() {
    EasyReadsTheme {
        ReadingSessionAddEditDialog(
            readingSession = ReadingSession(startPage = 25),
            onSave = { },
            onDismissRequest = { }
        )
    }
}
