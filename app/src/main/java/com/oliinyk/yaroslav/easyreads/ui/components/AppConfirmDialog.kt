package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R

@Composable
fun AppConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.confirmation_dialog__button__ok_text))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.confirmation_dialog__button__cancel_text))
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        }
    )
}
