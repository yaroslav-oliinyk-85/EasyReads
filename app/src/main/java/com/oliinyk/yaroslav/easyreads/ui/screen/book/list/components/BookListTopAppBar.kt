package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconTopBarButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppNavigationBackIconButton
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopAppBar(
    booksCount: Int,
    bookShelvesType: BookShelvesType?,
    holderSize: HolderSize,
    navBack: () -> Unit,
    onHolderSizeChange: (HolderSize) -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text =
                        when (bookShelvesType) {
                            BookShelvesType.WANT_TO_READ ->
                                stringResource(
                                    R.string.book_list__toolbar__title_want_to_read_text,
                                    booksCount,
                                )
                            BookShelvesType.READING ->
                                stringResource(
                                    R.string.book_list__toolbar__title_reading_text,
                                    booksCount,
                                )
                            BookShelvesType.FINISHED ->
                                stringResource(
                                    R.string.book_list__toolbar__title_finished_text,
                                    booksCount,
                                )
                            else ->
                                stringResource(
                                    R.string.book_list__toolbar__title_all_text,
                                    booksCount,
                                )
                        },
                )
            }
        },
        navigationIcon = {
            AppNavigationBackIconButton { navBack() }
        },
        actions = {
            IconButton(
                onClick = { menuExpanded = true },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menu_item__more_text),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
            ) {
                // Size Large
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_large_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.LARGE)
                    },
                    trailingIcon = {
                        if (holderSize == HolderSize.LARGE) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription =
                                    stringResource(
                                        R.string.menu_item__book_list__image_size_large_text,
                                    ),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                )
                // Size Default
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_default_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.DEFAULT)
                    },
                    trailingIcon = {
                        if (holderSize == HolderSize.DEFAULT) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription =
                                    stringResource(
                                        R.string.menu_item__book_list__image_size_default_text,
                                    ),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                )
                // Size Small
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.menu_item__book_list__image_size_small_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onHolderSizeChange(HolderSize.SMALL)
                    },
                    trailingIcon = {
                        if (holderSize == HolderSize.SMALL) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription =
                                    stringResource(
                                        R.string.menu_item__book_list__image_size_small_text,
                                    ),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}

@Preview(
    showBackground = true,
)
@Composable
private fun BookListTopAppBarPreview() {
    EasyReadsTheme {
        BookListTopAppBar(
            booksCount = 5,
            bookShelvesType = BookShelvesType.READING,
            holderSize = HolderSize.SMALL,
            navBack = {},
            onHolderSizeChange = {},
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun BookListTopAppBarDarkPreview() {
    EasyReadsTheme {
        BookListTopAppBar(
            booksCount = 5,
            bookShelvesType = BookShelvesType.READING,
            holderSize = HolderSize.SMALL,
            navBack = {},
            onHolderSizeChange = {},
        )
    }
}
