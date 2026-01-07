package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconTopBarButton
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLibraryTopAppBar(
    onAddBookClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.my_library__toolbar__title_text))
        },
        actions = {
            AppIconTopBarButton(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.menu_item__add_text),
                onClick = { onAddBookClick() },
            )
            AppIconTopBarButton(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.menu_item__settings_text),
                onClick = { onSettingsClick() },
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun MyLibraryTopAppBarPreview() {
    EasyReadsTheme {
        MyLibraryTopAppBar(
            onAddBookClick = {},
            onSettingsClick = {},
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun MyLibraryTopAppBarDarkPreview() {
    EasyReadsTheme {
        MyLibraryTopAppBar(
            onAddBookClick = {},
            onSettingsClick = {},
        )
    }
}
