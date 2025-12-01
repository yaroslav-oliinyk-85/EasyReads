package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        // CST
        thickness = Dimens.appDividerThickness,
        color = MaterialTheme.colorScheme.background,
    )
}
