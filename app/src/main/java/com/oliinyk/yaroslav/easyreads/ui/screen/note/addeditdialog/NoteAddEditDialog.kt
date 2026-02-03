package com.oliinyk.yaroslav.easyreads.ui.screen.note.addeditdialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.extension.toBookPage
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun NoteAddEditDialog(
    note: Note,
    pagesCount: Int,
    onSave: (Note) -> Unit,
    onDismissRequest: () -> Unit,
    isRemoveButtonEnabled: Boolean = false,
    onRemove: (Note) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        val errorMessageText = stringResource(R.string.note_add_edit_dialog__error__message_text)
        var noteText by rememberSaveable { mutableStateOf(note.text) }
        var notePage by rememberSaveable { mutableIntStateOf(note.page ?: 0) }
        var noteTextErrorMessage by rememberSaveable { mutableStateOf("") }

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
                NoteTextEditField(
                    noteText = noteText,
                    noteTextErrorMessage = noteTextErrorMessage,
                    onValueChange = { value ->
                        noteText = value

                        if (noteText.isBlank()) {
                            noteTextErrorMessage = errorMessageText
                        } else if (noteTextErrorMessage.isNotBlank()) {
                            noteTextErrorMessage = ""
                        }
                    },
                )

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                NotePageEditField(
                    notePageNumberText = if (notePage == 0) "" else notePage.toString(),
                    onValueChange = { value ->
                        notePage = value.toBookPage().coerceIn(0..pagesCount)
                    },
                )

                AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalMedium))

                SaveButton(
                    onClick = {
                        if (noteText.isBlank()) {
                            noteTextErrorMessage = errorMessageText
                        } else {
                            onSave(
                                note.copy(
                                    text = noteText.trim(),
                                    page = notePage,
                                ),
                            )
                        }
                    },
                )

                if (isRemoveButtonEnabled) {
                    Spacer(Modifier.height(Dimens.spacerHeightSmall))

                    RemoveButton(onClick = { onRemove(note) })
                }

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                // --- cancel button ---
                CancelButton(onClick = onDismissRequest)
            }
        }
    }
}

@Composable
private fun NoteTextEditField(
    noteText: String,
    noteTextErrorMessage: String,
    onValueChange: (String) -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.note_add_edit_dialog__label__note_text),
        value = noteText,
        hint = stringResource(R.string.note_add_edit_dialog__hint__enter_note_text),
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Default,
            ),
        singleLine = false,
        minLines = Dimens.noteTextMinLines,
        onValueChange = onValueChange,
        labelError = noteTextErrorMessage,
    )
}

@Composable
private fun NotePageEditField(
    notePageNumberText: String,
    onValueChange: (String) -> Unit,
) {
    AppEditField(
        label = stringResource(R.string.note_add_edit_dialog__label__note_page_text),
        value = notePageNumberText,
        hint = stringResource(R.string.note_add_edit_dialog__hint__enter_note_page_text),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        onValueChange = onValueChange,
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
            text = stringResource(R.string.dialog__button__save_text),
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
            ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
    ) {
        Text(
            text = stringResource(R.string.dialog__button__remove_text),
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
        onClick = onClick,
        text = stringResource(R.string.dialog__button__cancel_text),
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteAddEditDialogPreview() {
    EasyReadsTheme {
        NoteAddEditDialog(
            note = Note(),
            pagesCount = 250,
            onSave = {},
            onDismissRequest = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteAddEditDialogWithRemoveButtonPreview() {
    EasyReadsTheme {
        NoteAddEditDialog(
            note = Note(),
            pagesCount = 250,
            isRemoveButtonEnabled = true,
            onSave = {},
            onDismissRequest = {},
            onRemove = {},
        )
    }
}
