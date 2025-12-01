package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list.components

import androidx.compose.foundation.layout.Row
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
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingSessionListTopAppBar(
    sessionsCount: Int
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.reading_session_list__toolbar__title_text,
                        sessionsCount
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
fun ReadingSessionListTopAppBarPreview() {
    EasyReadsTheme {
        ReadingSessionListTopAppBar(
            sessionsCount = 5
        )
    }
}
