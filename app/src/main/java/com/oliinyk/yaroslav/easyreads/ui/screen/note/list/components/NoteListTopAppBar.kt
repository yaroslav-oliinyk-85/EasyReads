package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconTopBarButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    noteSize: Int,
    onAdd: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                stringResource(
                    R.string.note_list__toolbar__title_text,
                    noteSize)
            )
        },
        actions = {
            AppIconTopBarButton(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.menu_item__add_text),
                onClick = onAdd
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}