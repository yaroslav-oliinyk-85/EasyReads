package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.list.components

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.components.AppIconButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingSessionListItem(
    readingSession: ReadingSession,
    onClickedEdit: (ReadingSession) -> Unit,
    onClickedRemove: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(Dimens.paddingAllSmall)
        ) {
            // --- reading summery row ---
            Row {
                // --- read time ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_time_text,
                        readingSession.readHours,
                        readingSession.readMinutes
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                // --- read pages hour ---
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(
                        R.string.book_details__label__book_read_pages_hour_text,
                        readingSession.readPagesHour
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                // --- read pages ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_pages_text,
                        readingSession.readPages
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
            }

            // --- secondary row (date + page range) ---
            Row(
                modifier = Modifier.padding(top = Dimens.paddingTopSmall),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // --- started date + time ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__date_text,
                        DateFormat.format(
                            stringResource(R.string.date_and_time_format),
                            readingSession.startedDate
                        ).toString()
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                // --- start/end page ---
                Text(
                    text = stringResource(
                        R.string.reading_session_list_item__label__read_end_page_text,
                        readingSession.startPage,
                        readingSession.endPage
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))

            // --- Footer Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // --- remove button ---
                AppIconButton(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.menu_item__remove_text),
                    onClick = { onClickedRemove(readingSession) }
                )

                // --- edit button ---
                AppIconButton(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.menu_item__edit_text),
                    onClick = { onClickedEdit(readingSession) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingSessionListItemPreview() {
    ReadingSessionListItem(
        readingSession = ReadingSession(
            readTimeInMilliseconds = 600000L,
            startPage = 5,
            endPage = 30,
            readPages = 25
        ),
        onClickedEdit = { },
        onClickedRemove = { }
    )
}
