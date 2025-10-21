package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppEditField(
    label: String,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    minLines: Int = 1,
    labelError: String = ""
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = Dimens.paddingBottomTiny),
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                .background(MaterialTheme.colorScheme.background),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            singleLine = singleLine,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
            isError = labelError.isNotBlank()
        )
        if (labelError.isNotBlank()) {
            Text(
                text = labelError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}