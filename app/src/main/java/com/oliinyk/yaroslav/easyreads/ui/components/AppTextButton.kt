package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content,
        // CST
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
        contentPadding =
            PaddingValues(
                horizontal = Dimens.buttonContentPaddingHorizontal,
                vertical = Dimens.buttonContentPaddingVertical,
            ),
        border =
            BorderStroke(
                width = Dimens.buttonBorderWith,
                color = MaterialTheme.colorScheme.primary,
            ),
    )
}
