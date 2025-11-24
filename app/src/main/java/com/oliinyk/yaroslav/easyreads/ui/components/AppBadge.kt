package com.oliinyk.yaroslav.easyreads.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun AppBadge(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = Dimens.appBadgeFontSize,
    style: TextStyle = LocalTextStyle.current,
    borderColor: Color = Color.Unspecified
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(Dimens.appBadgeRoundedCornerShapeSize)
            )
            .border(
                width = Dimens.appBadgeBorderWith,
                color = borderColor.takeOrElse {
                    style.color.takeOrElse {
                        LocalContentColor.current
                    }
                },
                shape = RoundedCornerShape(Dimens.appBadgeRoundedCornerShapeSize)
            )
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            style = style,
            modifier = Modifier
                .padding(
                    horizontal = Dimens.paddingHorizontalSmall,
                    vertical = Dimens.paddingVerticalTiny
                )
        )
    }
}