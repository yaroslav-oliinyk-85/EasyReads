package com.oliinyk.yaroslav.easyreads.ui.screen.book.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun SortOrderButton(
    modifier: Modifier = Modifier,
    onSortingOrderChange: () -> Unit

) {
    Box(
        modifier = modifier
            .size(Dimens.bookListItemSortOrderSize)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
            ).border(
                width = Dimens.AppComponents.appTextButtonBorderWith,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
            )
    ) {
        IconButton(
            modifier = Modifier.fillMaxSize(),
            onClick = onSortingOrderChange
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_button_sort_order),
                contentDescription = stringResource(R.string.book_list__button__sorted_order_content_description_text),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}