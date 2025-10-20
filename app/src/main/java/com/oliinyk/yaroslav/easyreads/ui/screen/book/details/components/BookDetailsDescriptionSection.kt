package com.oliinyk.yaroslav.easyreads.ui.screen.book.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppDivider
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookDetailsDescriptionSection(
    description: String,
    modifier: Modifier = Modifier
) {
    val descriptionMinLines = 5
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.paddingAllSmall)
        ) {
            // --- Label ---
            Text(
                text = stringResource(R.string.book_details__label__book_description_text),
                style = MaterialTheme.typography.bodyLarge
            )
            AppDivider(Modifier.padding(vertical = Dimens.paddingVerticalSmall))
            // --- Description text ---
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                minLines = descriptionMinLines
            )
        }
    }
}
