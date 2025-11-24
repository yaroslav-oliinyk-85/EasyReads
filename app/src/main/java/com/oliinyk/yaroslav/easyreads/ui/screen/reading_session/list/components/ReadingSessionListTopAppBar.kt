package com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

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
                        R.string.reading_session_list__toolbar__title_text
                    )
                )
                Spacer(Modifier.width(Dimens.spacerWidthSmall))
                AppBadge(
                    text = sessionsCount.toString(),
                    borderColor = Color.Unspecified
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}