package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppBadge
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconTopBarButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    noteSize: Int,
    onAdd: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(
                        R.string.note_list__toolbar__title_text
                    )
                )
                Spacer(Modifier.width(Dimens.spacerWidthSmall))
                AppBadge(
                    text = noteSize.toString(),
                    borderColor = Color.Unspecified
                )
            }
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