package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text =
                    stringResource(
                        R.string.reading_goal__toolbar__title_text,
                        LocalDate.now().year,
                    ),
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}
