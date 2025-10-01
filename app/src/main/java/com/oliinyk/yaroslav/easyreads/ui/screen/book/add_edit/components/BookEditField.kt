package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookEditField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = Dimens.paddingBottomTiny),
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(hint) },
            singleLine = singleLine,
            minLines = minLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}