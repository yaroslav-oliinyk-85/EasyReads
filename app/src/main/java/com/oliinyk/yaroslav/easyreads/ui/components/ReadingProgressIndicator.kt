package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun ReadingProgressIndicator(
    modifier: Modifier = Modifier,
    percentage: Int
) {
    Box(
        modifier = modifier.size(Dimens.bookListItemPercentageSize),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = percentage / 100f,
            strokeWidth = Dimens.bookListItemPercentageStrokeWidth,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.background
        )
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}