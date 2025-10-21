package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.presentation.note.list.NoteListStateUi
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun NoteListContent(
    stateUi: NoteListStateUi,
    onEdit: (Note) -> Unit,
    onRemove: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (stateUi.notes.isEmpty()) {
            Text(
                text = stringResource(R.string.note_list__label__no_notes_text),
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = Dimens.paddingHorizontalMedium,
                    vertical = Dimens.paddingVerticalSmall
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall),
            ) {
                items(items = stateUi.notes) { note ->
                    NoteListItemCell(
                        note = note,
                        onEdit = onEdit,
                        onRemove = onRemove
                    )
                }
            }
        }
    }
}