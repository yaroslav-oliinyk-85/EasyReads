package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton

@Composable
fun SeeAllReadingSessionButton(
    quantity: Int,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextButton(
        onClick = onSeeAll,
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                R.string.book_details__button__see_all_reading_sessions_text,
                quantity
            ),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}