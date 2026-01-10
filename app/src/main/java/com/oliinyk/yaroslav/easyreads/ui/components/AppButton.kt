package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        content = content,
        // CST
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize),
        contentPadding =
            PaddingValues(
                horizontal = Dimens.buttonContentPaddingHorizontal,
                vertical = Dimens.buttonContentPaddingVertical,
            ),
    )
}
