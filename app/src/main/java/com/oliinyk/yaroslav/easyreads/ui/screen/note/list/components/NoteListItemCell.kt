package com.oliinyk.yaroslav.easyreads.ui.screen.note.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NoteListItemCell(
    note: Note,
    onEdit: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onEdit(note) },
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
                style = MaterialTheme.typography.bodyMedium,
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
                    style = MaterialTheme.typography.bodySmall,
                )

                // --- page ---
                if (note.page != null) {
                    Text(
                        text =
                            stringResource(
                                R.string.note_list_item__label__page_text,
                                note.page,
                            ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListItemCellPreview() {
    EasyReadsTheme {
        NoteListItemCell(
            note =
                Note(
                    text = "Note text",
                    page = 25,
                    addedAt = LocalDateTime.now(),
                ),
            onEdit = { },
        )
    }
}
