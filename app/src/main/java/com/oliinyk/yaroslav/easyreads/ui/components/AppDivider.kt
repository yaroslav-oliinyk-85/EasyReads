package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color
) {
    Divider(
        modifier = modifier,
        //CST
        thickness = Dimens.AppComponents.appDividerThickness,
        color = MaterialTheme.colorScheme.background
    )
}