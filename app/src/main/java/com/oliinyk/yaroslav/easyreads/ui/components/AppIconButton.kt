package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBorder: Boolean = true,
) {
    AppIconButton(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        enabledBorder = enabledBorder,
    )
}

@Composable
fun AppIconButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledBorder: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        modifier =
            modifier.then(
                if (enabledBorder) {
                    Modifier.border(
                        width = Dimens.buttonBorderWith,
                        color =
                            if (enabled) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.inversePrimary
                            },
                        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
                    )
                } else {
                    Modifier
                },
            ),
        enabled = enabled,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
        )
    }
}
