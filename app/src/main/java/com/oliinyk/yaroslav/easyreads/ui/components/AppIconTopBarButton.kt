package com.oliinyk.yaroslav.easyreads.ui.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun AppIconTopBarButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIconTopBarButton(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun AppIconTopBarButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        modifier = modifier,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun AppIconTopBarButtonPreview() {
    EasyReadsTheme {
        AppIconTopBarButton(
            imageVector = Icons.Default.Add,
            contentDescription = "",
            onClick = { },
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppIconTopBarButtonDarkPreview() {
    EasyReadsTheme {
        AppIconTopBarButton(
            imageVector = Icons.Default.Add,
            contentDescription = "",
            onClick = { },
        )
    }
}
