package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEditFieldWithDropdownMenu(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    suggestions: List<String>,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val filteredSuggestions = remember(value) {
        if (value.length <= 1) {
            emptyList()
        } else {
            suggestions.filter { it.contains(value, true) }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        BookEditField(
            label = label,
            value = value,
            hint = hint,
            keyboardType = keyboardType,
            singleLine = singleLine,
            minLines = minLines,
            onValueChange = { newValue ->
                onValueChange(newValue)
                expanded = true
            }
        )
        DropdownMenu(
            expanded = expanded && filteredSuggestions.isNotEmpty(),
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false)
        ) {
            filteredSuggestions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}