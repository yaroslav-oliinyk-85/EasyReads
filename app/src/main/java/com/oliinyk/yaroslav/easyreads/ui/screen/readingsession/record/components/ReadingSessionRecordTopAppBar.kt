package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppNavigationBackIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingSessionRecordTopAppBar(navBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.reading_session_record__toolbar__title_text))
        },
        navigationIcon = {
            AppNavigationBackIconButton(navBack = navBack)
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}
