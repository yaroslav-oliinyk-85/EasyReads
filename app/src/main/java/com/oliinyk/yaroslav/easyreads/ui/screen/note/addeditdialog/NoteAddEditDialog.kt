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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.BOOK_PAGE_AMOUNT_MAX_LENGTH
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun NoteAddEditDialog(
    note: Note,
    onSave: (Note) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        val noteTextMinLines = 3
        val errorMessageText = stringResource(R.string.note_add_edit_dialog__error__message_text)
        var noteText by rememberSaveable { mutableStateOf(note.text) }
        var notePageNumberText by rememberSaveable { mutableStateOf(note.page?.toString() ?: "") }
        var noteTextLabelError by rememberSaveable { mutableStateOf("") }

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
                // --- note text ---
                AppEditField(
                    label = stringResource(R.string.note_add_edit_dialog__label__note_text),
                    value = noteText,
                    hint = stringResource(R.string.note_add_edit_dialog__hint__enter_note_text),
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                    singleLine = false,
                    minLines = noteTextMinLines,
                    onValueChange = { value ->
                        noteText = value
                        noteTextLabelError =
                            if (noteText.isBlank()) {
                                errorMessageText
                            } else {
                                ""
                            }
                    },
                    labelError = noteTextLabelError,
                )

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                // --- note page ---
                AppEditField(
                    label = stringResource(R.string.note_add_edit_dialog__label__note_page_text),
                    value = notePageNumberText,
                    hint = stringResource(R.string.note_add_edit_dialog__hint__enter_note_page_text),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    onValueChange = { value ->
                        if (value.isBlank() || value.isDigitsOnly()) {
                            notePageNumberText = value.take(BOOK_PAGE_AMOUNT_MAX_LENGTH)
                        }
                    },
                )

                AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalMedium))

                // --- save button ---
                AppButton(
                    onClick = {
                        if (noteText.isBlank()) {
                            noteTextLabelError = errorMessageText
                        } else {
                            onSave(
                                note.copy(
                                    text = noteText.trim(),
                                    page = if (notePageNumberText.isNotBlank()) notePageNumberText.toInt() else null,
                                ),
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.dialog__button__save_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                Spacer(Modifier.height(Dimens.spacerHeightSmall))

                // --- cancel button ---
                AppTextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.dialog__button__cancel_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteAddEditDialogPreview() {
    EasyReadsTheme {
        NoteAddEditDialog(
            note = Note(),
            onSave = {},
            onDismissRequest = {},
        )
    }
}
