package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.presentation.book.list.StateUiBookList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreenTopAppBar(
    stateUi: StateUiBookList,
    onAddBookClick: () -> Unit,
    onHolderSizeChange: (HolderSize) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = stringResource(
                    R.string.book_list__toolbar__title_text,
                    stateUi.books.size
                )
            )
        },
        actions = {

            IconButton(
                onClick = { onAddBookClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.menu_item__book_list__add_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(
                onClick = { menuExpanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menu_item__book_list__more_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                // Size Large
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_large_text),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.LARGE)
                    },
                    trailingIcon = {
                        if (stateUi.holderSize == HolderSize.LARGE) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.menu_item__book_list__image_size_large_text),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                // Size Default
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_default_text),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.DEFAULT)
                    },
                    trailingIcon = {
                        if (stateUi.holderSize == HolderSize.DEFAULT) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.menu_item__book_list__image_size_default_text),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                // Size Small
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_small_text),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.SMALL)
                    },
                    trailingIcon = {
                        if (stateUi.holderSize == HolderSize.SMALL) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.menu_item__book_list__image_size_small_text),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}