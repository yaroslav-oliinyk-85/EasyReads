package com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.components

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
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.extension.toBookPage
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppEditField
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun ReadingGoalChangeDialog(
    readingGoal: ReadingGoal,
    onDismissRequest: () -> Unit,
    onSave: (ReadingGoal) -> Unit
) {
    var goalTextState by rememberSaveable { mutableStateOf(readingGoal.goal) }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
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
                AppEditField(
                    label = stringResource(
                        R.string.reading_goal_change_dialog__label__goal_text
                    ),
                    value = if (goalTextState == 0) "" else goalTextState.toString(),
                    onValueChange = { value ->
                        goalTextState = value.toBookPage()
                    },
                    hint = stringResource(
                        R.string.reading_goal_change_dialog__hint__enter_goal_text
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalMedium))

                // --- Buttons Save ---
                AppButton(
                    onClick = {
                        onSave(readingGoal.copy(goal = goalTextState))
                        onDismissRequest()
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
private fun ReadingGoalChangeDialogPreview() {
    EasyReadsTheme {
        ReadingGoalChangeDialog(
            readingGoal = ReadingGoal().copy(goal = 12),
            onSave = {},
            onDismissRequest = {}
        )
    }
}