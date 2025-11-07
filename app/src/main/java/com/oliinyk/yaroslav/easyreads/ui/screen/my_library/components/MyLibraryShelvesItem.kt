package com.oliinyk.yaroslav.easyreads.ui.screen.my_library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun MyLibraryShelvesItem(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.arrangementHorizontalSpaceSmall)
    ) {
        Text(
            text = label,
            fontSize = Dimens.appTitleMediumFontSize,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .weight(1.0f)
                .padding(
                    vertical = Dimens.paddingVerticalMedium,
                    horizontal = Dimens.paddingHorizontalSmall
                )
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = label
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyLibraryShelvesItemPreview() {
    EasyReadsTheme {
        MyLibraryShelvesItem(
            label = "Finished",
            onClick = { }
        )
    }
}
