package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun MyLibraryShelveItem(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = Dimens.appTitleMediumFontSize,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .weight(1.0f)
                .padding(vertical = Dimens.paddingVerticalMedium)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "todo"
        )
    }
}