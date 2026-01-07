package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun AppConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            AppButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.confirmation_dialog__button__ok_text),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = Dimens.paddingHorizontalMedium),
                )
            }
        },
        dismissButton = {
            AppTextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.confirmation_dialog__button__cancel_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(
                text = message,
                maxLines = Dimens.confirmDialogMessageMaxLine,
                overflow = TextOverflow.Ellipsis,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun AppConfirmDialogPreview() {
    EasyReadsTheme {
        AppConfirmDialog(
            title = "Remove Note Dialog",
            message = "Note will be removed!",
            onConfirm = {},
            onDismiss = {},
        )
    }
}
