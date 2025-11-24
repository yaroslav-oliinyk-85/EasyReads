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
    val appBadgeRoundedCornerShapeSize = 8.dp

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
    val paddingEndMedium = 16.dp

    val paddingAllTiny = 4.dp
    val paddingAllSmall = 8.dp
    val paddingAllMedium = 16.dp

    val buttonContentPaddingVertical = 12.dp
    val buttonContentPaddingHorizontal = 12.dp
    val buttonBorderWith = 1.dp
    val appBadgeBorderWith = 1.dp

    val arrangementHorizontalSpaceTiny = 4.dp
    val arrangementHorizontalSpaceSmall = 8.dp
    val arrangementVerticalSpaceTiny = 4.dp
    val arrangementVerticalSpaceSmall = 8.dp
    val arrangementVerticalSpaceMedium = 16.dp

    val readingGoalBookCoverImageSize = DpSize(80.dp, 130.dp)
    val bookAddEditBookCoverImageSize = DpSize(width = 160.dp, height = 240.dp)
    val bookDetailsBookCoverImageSize = DpSize(width = 160.dp, height = 240.dp)
    val bookDetailsBookCoverImageScaledSize = DpSize(width = 320.dp, height = 480.dp)
    val readingSessionRecordBookCoverImageSize = DpSize(width = 120.dp, height = 180.dp)

    val bookListItemPercentageSize = 44.dp
    val bookListItemSortOrderSize = 42.dp
    val bookListItemPercentageStrokeWidth = 4.dp

    val dropdownMenuItemContentPaddingMedium = PaddingValues(
        horizontal = 16.dp,
        vertical = 0.dp
    )

    val startReadingSessionButtonSize = 96.dp
    val startReadingSessionIconSize = 64.dp

    val linearProgressIndicatorHeight = 5.dp

    val appDividerThickness = 2.dp


    // ----- text -----
    val appTitleMediumFontSize = 18.sp

    val appBadgeFontSize = 16.sp

    val readingSessionRecordBookTitleMaxLines = 3
    val readingSessionRecordBookAuthorMaxLines = 1

    val bookListItemShelveTextMaxLines = 2


    // ----- animation -----
    val animationEnterDurationMillis: Int = 200
    val animationExitDurationMillis: Int = animationEnterDurationMillis

    val animationPopEnterDurationMillis: Int = 300
    val animationPopExitDurationMillis: Int = animationPopEnterDurationMillis

    val animationDelayDurationMillis: Int = 200
}