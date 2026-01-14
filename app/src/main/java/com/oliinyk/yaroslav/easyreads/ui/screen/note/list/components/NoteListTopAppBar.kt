package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconTopBarButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppNavigationBackIconButton
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    noteSize: Int,
    navBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(
                        R.string.note_list__toolbar__title_text,
                        noteSize,
                    ),
                )
            }
        },
        navigationIcon = {
            AppNavigationBackIconButton(navBack = navBack)
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
private fun NoteListTopAppBarPreview() {
    EasyReadsTheme {
        NoteListTopAppBar(
            noteSize = 5,
            navBack = {},
        )
    }
}
