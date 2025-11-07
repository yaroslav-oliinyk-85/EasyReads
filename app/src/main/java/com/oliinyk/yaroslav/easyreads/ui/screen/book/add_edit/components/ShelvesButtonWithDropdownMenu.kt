package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.presentation.book.add_edit.BookAddEditStateUi
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton

@Composable
fun ShelvesButtonWithDropdownMenu(
    modifier: Modifier = Modifier,
    stateUi: BookAddEditStateUi,
    onEvent: (BookAddEditEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        AppTextButton(
            modifier = modifier,
            onClick = { expanded = true }
        ) {
            Text(
                text = when (stateUi.book.shelf) {
                    BookShelvesType.FINISHED ->
                        stringResource(R.string.book_details__button__shelf_finished_text)

                    BookShelvesType.READING ->
                        stringResource(R.string.book_details__button__shelf_reading_text)

                    BookShelvesType.WANT_TO_READ ->
                        stringResource(R.string.book_details__button__shelf_want_to_read_text)
                }
            )
        }
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BookShelvesType.entries.forEach { shelf ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = when(shelf) {
                                BookShelvesType.FINISHED ->
                                    stringResource(R.string.book_details__button__shelf_finished_text)
                                BookShelvesType.READING ->
                                    stringResource(R.string.book_details__button__shelf_reading_text)
                                BookShelvesType.WANT_TO_READ ->
                                    stringResource(R.string.book_details__button__shelf_want_to_read_text)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        expanded = false
                        onEvent(BookAddEditEvent.ShelveChanged(shelf))
                    }
                )
            }
        }
    }
}