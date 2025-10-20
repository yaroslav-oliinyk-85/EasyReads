package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsIsbnSection(
    modifier: Modifier = Modifier,
    isbn: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.paddingAllSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- Label ---
            Text(
                text = stringResource(R.string.book_details__label__book_isbn_text),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(Dimens.spacerWidthSmall))
            // --- ISBN text ---
            Text(
                text = isbn,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
