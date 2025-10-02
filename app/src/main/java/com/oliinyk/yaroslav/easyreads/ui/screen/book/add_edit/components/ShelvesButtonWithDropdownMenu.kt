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
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelveType
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
                text = when (stateUi.book.shelve) {
                    BookShelveType.FINISHED ->
                        stringResource(R.string.book_details__label__shelve_finished_text)

                    BookShelveType.READING ->
                        stringResource(R.string.book_details__label__shelve_reading_text)

                    BookShelveType.WANT_TO_READ ->
                        stringResource(R.string.book_details__label__shelve_want_to_read_text)
                }
            )
        }
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BookShelveType.entries.forEach { shelve ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = when(shelve) {
                                BookShelveType.FINISHED ->
                                    stringResource(R.string.book_details__label__shelve_finished_text)
                                BookShelveType.READING ->
                                    stringResource(R.string.book_details__label__shelve_reading_text)
                                BookShelveType.WANT_TO_READ ->
                                    stringResource(R.string.book_details__label__shelve_want_to_read_text)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        expanded = false
                        onEvent(BookAddEditEvent.ShelveChanged(shelve))
                    }
                )
            }
        }
    }
}