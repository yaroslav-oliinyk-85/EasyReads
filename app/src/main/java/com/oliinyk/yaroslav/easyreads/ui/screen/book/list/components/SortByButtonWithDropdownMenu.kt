package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookSorting
import com.oliinyk.yaroslav.easyreads.domain.model.BookSortingType
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortByButtonWithDropdownMenu(
    modifier: Modifier = Modifier,
    currentSorting: BookSorting,
    onSortingChange: (BookSortingType) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier =
            modifier
                .height(Dimens.bookListItemSortOrderSize)
                .clickable { expanded = !expanded }
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
                ).border(
                    width = Dimens.buttonBorderWith,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
                ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier.padding(start = Dimens.paddingStartSmall),
            text =
                stringResource(
                    R.string.book_list__button__sorted_by_text,
                    stringResource(
                        when (currentSorting.bookSortingType) {
                            BookSortingType.TITLE -> R.string.book_list__button__sorted_by_title_text
                            BookSortingType.AUTHOR -> R.string.book_list__button__sorted_by_author_text
                            BookSortingType.ADDED_DATE -> R.string.book_list__button__sorted_by_added_date_text
                            BookSortingType.UPDATED_DATE -> R.string.book_list__button__sorted_by_updated_date_text
                        },
                    ),
                ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = Dimens.paddingEndTiny),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            BookSortingType.entries.forEach { type ->
                DropdownMenuItem(
                    contentPadding = Dimens.dropdownMenuItemContentPaddingMedium,
                    text = {
                        Text(
                            text =
                                stringResource(
                                    R.string.book_list__dropdown_menu_item__sort_by_text,
                                    stringResource(
                                        when (type) {
                                            BookSortingType.TITLE ->
                                                R.string.book_list__button__sorted_by_title_text
                                            BookSortingType.AUTHOR ->
                                                R.string.book_list__button__sorted_by_author_text
                                            BookSortingType.ADDED_DATE ->
                                                R.string.book_list__button__sorted_by_added_date_text
                                            BookSortingType.UPDATED_DATE ->
                                                R.string.book_list__button__sorted_by_updated_date_text
                                        },
                                    ),
                                ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    onClick = {
                        expanded = false
                        onSortingChange(type)
                    },
                )
            }
        }
    }
}
