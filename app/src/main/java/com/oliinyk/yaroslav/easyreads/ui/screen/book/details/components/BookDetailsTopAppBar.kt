package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsTopAppBar(
    title: String,
    onEditBook: () -> Unit,
    onRemoveBook: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(
                onClick = { menuExpanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menu_item__more_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__edit_text),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onEditBook()
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.menu_item__edit_text),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__remove_text),
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onRemoveBook()
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.menu_item__remove_text),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
            /*
            IconButton(onClick = onEditBook) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.menu_item__book_details__edit_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = onRemoveBook) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.menu_item__book_details__remove_text),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            */
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
