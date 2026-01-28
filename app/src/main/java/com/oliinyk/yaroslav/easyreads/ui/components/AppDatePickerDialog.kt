package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun AppDatePickerDialog(
    datePickerState: DatePickerState,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AppButton(
                modifier = Modifier.padding(end = Dimens.paddingEndMedium, bottom = Dimens.paddingBottomSmall),
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val selectedDate =
                            Instant
                                .ofEpochMilli(selectedMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                    onDismissRequest()
                },
            ) {
                Text(
                    text = stringResource(R.string.confirmation_dialog__button__ok_text),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = Dimens.paddingHorizontalMedium),
                )
            }
        },
        dismissButton = {
            AppTextButton(
                modifier = Modifier.padding(bottom = Dimens.paddingBottomSmall),
                onClick = onDismissRequest,
            ) {
                Text(
                    stringResource(R.string.confirmation_dialog__button__cancel_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        content = {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
            )
        },
    )
}
