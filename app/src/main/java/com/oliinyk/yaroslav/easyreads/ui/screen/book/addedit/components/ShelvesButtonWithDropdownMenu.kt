package com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditEvent
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditUiState
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ShelvesButtonWithDropdownMenu(
    modifier: Modifier = Modifier,
    uiState: BookAddEditUiState,
    onEvent: (BookAddEditEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
    ) {
        AppTextButton(
            modifier =
                modifier
                    .clip(RoundedCornerShape(Dimens.roundedCornerShapeSize))
                    .background(MaterialTheme.colorScheme.background),
            onClick = { expanded = true },
        ) {
            Text(
                text =
                    when (uiState.book.shelf) {
                        BookShelvesType.FINISHED ->
                            stringResource(R.string.book_details__button__shelf_finished_text)

                        BookShelvesType.READING ->
                            stringResource(R.string.book_details__button__shelf_reading_text)

                        BookShelvesType.WANT_TO_READ ->
                            stringResource(R.string.book_details__button__shelf_want_to_read_text)
                    },
                maxLines = Dimens.bookAddEditDialogShelfTextMaxLines,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            BookShelvesType.entries.forEach { shelf ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text =
                                when (shelf) {
                                    BookShelvesType.FINISHED ->
                                        stringResource(R.string.book_details__button__shelf_finished_text)
                                    BookShelvesType.READING ->
                                        stringResource(R.string.book_details__button__shelf_reading_text)
                                    BookShelvesType.WANT_TO_READ ->
                                        stringResource(R.string.book_details__button__shelf_want_to_read_text)
                                },
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    onClick = {
                        expanded = false
                        onEvent(BookAddEditEvent.ShelveChanged(shelf))
                    },
                )
            }
        }
    }
}
