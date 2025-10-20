package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppIconButton(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun AppIconButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.border(
            width = Dimens.AppComponents.appTextButtonBorderWith,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
        )
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}