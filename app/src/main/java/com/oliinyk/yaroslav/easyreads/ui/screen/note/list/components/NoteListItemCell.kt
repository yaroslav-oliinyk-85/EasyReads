package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import java.time.format.DateTimeFormatter

@Composable
fun NoteListItemCell(
    note: Note,
    onEdit: (Note) -> Unit,
    onRemove: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingAllSmall),
        ) {
            // --- note text ---
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyLarge,
            )

            // --- added date + page ---
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.paddingTopMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // --- added date ---
                Text(
                    text =
                        note.addedAt.format(
                            DateTimeFormatter.ofPattern(
                                stringResource(R.string.date_and_time_format),
                            ),
                        ),
                    style = MaterialTheme.typography.labelMedium,
                )

                // --- page ---
                if (note.page != null) {
                    Text(
                        text =
                            stringResource(
                                R.string.note_list_item__label__page_text,
                                note.page,
                            ),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            // ---------- Divider ----------
            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // ---------- Footer buttons ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // --- remove button ---
                AppIconButton(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.menu_item__remove_text),
                    onClick = { onRemove(note) },
                )

                // --- edit button ---
                AppIconButton(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.menu_item__edit_text),
                    onClick = { onEdit(note) },
                )
            }
        }
    }
}
