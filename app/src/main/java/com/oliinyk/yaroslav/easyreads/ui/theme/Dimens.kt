package com.oliinyk.yaroslav.easyreads.ui.theme

import androidx.compose.ui.unit.dp

object Dimens {

    val spacerHeightSmall = 8.dp
    val spacerHeightMedium = 16.dp

    val roundedCornerShapeSize = 8.dp

    val paddingHorizontalSmall = 8.dp
    val paddingHorizontalMedium = 16.dp

    val paddingVerticalSmall = 8.dp
    val paddingVerticalMedium = 16.dp

    object AppComponents {
        val appDividerThickness = 2.dp

        val appTextButtonPaddingVertical = 8.dp
        val appTextButtonBorderWith = 1.dp
    }

    object MyLibraryScreen {
        val columnPaddingHorizontal = paddingHorizontalMedium
        val columnPaddingVertical = paddingVerticalSmall
        val lazyColumnVerticalArrangementSpace = 8.dp

        object ReadingGoalCard {
            val columnPaddingHorizontal = paddingHorizontalMedium
            val columnPaddingVertical = paddingVerticalSmall

            val linearProgressIndicatorHeight = 5.dp
        }

        object ShelvesCard {
            val columnPaddingHorizontal = paddingHorizontalMedium
            val columnPaddingVertical = paddingVerticalSmall
        }

        val shelveItemPaddingVertical = paddingVerticalMedium
    }
}