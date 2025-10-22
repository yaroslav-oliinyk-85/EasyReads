package com.oliinyk.yaroslav.easyreads.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {

    // ----- dp -----
    val spacerHeightSmall = 8.dp
    val spacerHeightMedium = 16.dp

    val spacerWidthSmall = 8.dp

    val roundedCornerShapeSize = 8.dp

    val paddingHorizontalSmall = 8.dp
    val paddingHorizontalMedium = 16.dp

    val paddingVerticalTiny = 4.dp
    val paddingVerticalSmall = 8.dp
    val paddingVerticalMedium = 16.dp

    val paddingTopTiny = 4.dp
    val paddingTopSmall = 8.dp
    val paddingTopMedium = 16.dp

    val paddingBottomTiny = 4.dp
    val paddingBottomSmall = 8.dp

    val paddingStartSmall = 8.dp

    val paddingEndTiny = 4.dp
    val paddingEndSmall = 8.dp

    val paddingAllTiny = 4.dp
    val paddingAllSmall = 8.dp
    val paddingAllMedium = 16.dp

    val arrangementHorizontalSpaceTiny = 4.dp
    val arrangementHorizontalSpaceSmall = 8.dp
    val arrangementVerticalSpaceTiny = 4.dp
    val arrangementVerticalSpaceSmall = 8.dp
    val arrangementVerticalSpaceMedium = 16.dp

    val readingGoalBookCoverImageSize = DpSize(80.dp, 130.dp)
    val bookAddEditBookCoverImageSize = DpSize(width = 160.dp, height = 240.dp)

    val bookListItemPercentageSize = 44.dp
    val bookListItemSortOrderSize = 42.dp
    val bookListItemPercentageStrokeWidth = 4.dp

    val dropdownMenuItemContentPaddingMedium = PaddingValues(
        horizontal = 16.dp,
        vertical = 0.dp
    )

    val bookDetailsCoverImageHeight = 240.dp
    val bookDetailsCoverImageWidth = 160.dp

    val startReadingSessionButtonSize = 96.dp
    val startReadingSessionIconSize = 64.dp

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

    // ----- sp -----
    val appTitleMediumFontSize = 18.sp
}