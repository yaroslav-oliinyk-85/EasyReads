package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R

@Composable
fun AppNavigationBackIconButton(navBack: () -> Unit) {
    IconButton(
        onClick = navBack,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = stringResource(R.string.menu_item__nav_back_text),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
