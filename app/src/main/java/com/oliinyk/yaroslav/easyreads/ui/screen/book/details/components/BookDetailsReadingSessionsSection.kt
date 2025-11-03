package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.add_edit.ReadingSessionAddEditDialog
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsReadingSessionsSection(
    modifier: Modifier = Modifier,
    sessions: List<ReadingSession>,
    onSeeAll: () -> Unit,
    onEdit: (ReadingSession) -> Unit
) {
    var editingReadingSession: ReadingSession? by remember { mutableStateOf(null) }
    editingReadingSession?.let { readingSession ->
        ReadingSessionAddEditDialog(
            readingSession = readingSession,
            onSave = { it
                onEdit(it)
                editingReadingSession = null
            },
            onDismissRequest = { editingReadingSession = null }
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(Dimens.paddingAllSmall)
        ) {
            if (sessions.isEmpty()) {
                Text(
                    text = stringResource(R.string.book_details__label__no_reading_sessions_text),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                val session = sessions.first()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // --- reading summery row ---
                        Row {
                            // --- read time ---
                            Text(
                                text = stringResource(
                                    R.string.reading_session_list_item__label__read_time_text,
                                    session.readHours,
                                    session.readMinutes
                                ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            // --- read pages hour ---
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(
                                    R.string.book_details__label__book_read_pages_hour_text,
                                    session.readPagesHour
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            // --- read pages ---
                            Text(
                                text = stringResource(
                                    R.string.reading_session_list_item__label__read_pages_text,
                                    session.readPages
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Right
                            )
                        }

                        // --- secondary row (date + page range) ---
                        Row(
                            modifier = Modifier.padding(top = Dimens.paddingTopTiny),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // --- started date + time ---
                            Text(
                                text = stringResource(
                                    R.string.reading_session_list_item__label__date_text,
                                    DateFormat.format(
                                        stringResource(R.string.date_and_time_format),
                                        session.startedDate
                                    ).toString()
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.weight(1f))
                            // --- start/end page ---
                            Text(
                                text = stringResource(
                                    R.string.reading_session_list_item__label__read_end_page_text,
                                    session.startPage,
                                    session.endPage
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Spacer(Modifier.width(Dimens.spacerWidthSmall))
                    // --- edit reading session button ---
                    AppIconButton(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.menu_item__edit_text),
                        onClick = { editingReadingSession = session }
                    )
                }
            }

            // ---------- Divider ----------
            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // ---------- Footer buttons ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SeeAllReadingSessionButton(
                    quantity = sessions.size,
                    onSeeAll = onSeeAll,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
