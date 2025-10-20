package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsNotesSection(
    notes: List<Note>,
    onSeeAll: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingAllSmall)
        ) {
            if (notes.isEmpty()) {
                // --- no notes text ---
                Text(
                    text = stringResource(R.string.book_details__label__no_notes_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                val lastNote = notes.first()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row {
                            // --- note text ---
                            Text(
                                text = lastNote.text,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 5,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(Modifier.height(Dimens.spacerHeightSmall))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // --- note added date text ---
                            Text(
                                text = DateFormat.format(
                                    stringResource(R.string.date_and_time_format),
                                    lastNote.addedDate
                                ).toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.weight(1f))
                            // --- note page text ---
                            lastNote.page?.let { page ->
                                Text(
                                    text = stringResource(
                                        R.string.note_list_item__label__page_text,
                                        page
                                    ),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(Dimens.spacerWidthSmall))
                    // --- edit note button ---
                    AppIconButton(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.menu_item__book_details__edit_text),
                        onClick = { onEdit(lastNote) }
                    )
                }
            }

            // ---------- Divider ----------

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // ---------- Footer buttons ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // --- see all notes button ---
                AppTextButton(
                    onClick = onSeeAll,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            R.string.book_details__button__see_all_notes_text,
                            notes.size
                        )
                    )
                }
                // --- add note button ---
                AppIconButton(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.menu_item__book_list__add_text),
                    onClick = onAdd
                )
            }
        }
    }
}
