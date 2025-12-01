package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.list.components

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
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingSessionListContent(
    readingSessions: List<ReadingSession>,
    onClickedEdit: (ReadingSession) -> Unit,
    onClickedRemove: (ReadingSession) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (readingSessions.isEmpty()) {
            Text(
                text = stringResource(R.string.reading_session_list__label__list_empty_text),
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.paddingAllMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.arrangementVerticalSpaceSmall)
            ) {
                items(readingSessions) { readingSession ->
                    ReadingSessionListItem(
                        readingSession = readingSession,
                        onClickedEdit = onClickedEdit,
                        onClickedRemove = onClickedRemove
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingSessionListContentPreview() {
    ReadingSessionListContent(
        readingSessions = listOf(ReadingSession(), ReadingSession()),
        onClickedEdit = {},
        onClickedRemove = {}
    )
}
