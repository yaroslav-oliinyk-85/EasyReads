package com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppButton
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

@Composable
fun BookAddEditBottomBar(
    onClickSave: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = Dimens.paddingHorizontalMedium,
                vertical = Dimens.paddingVerticalSmall
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.roundedCornerShapeSize)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.paddingAllMedium)
                .fillMaxWidth()
        ) {
            AppTextButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                onClick = onClickCancel,
                content = {
                    Text(
                        text = stringResource(R.string.book_add_edit__button__cancel_text),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )

            Spacer(Modifier.width(Dimens.spacerHeightMedium))

            AppButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                onClick = onClickSave,
                content = {
                    Text(
                        text = stringResource(R.string.book_add_edit__button__save_text),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun BookAddEditBottomButtonsPreview() {
    BookAddEditBottomBar(
        onClickSave = {},
        onClickCancel = {}
    )
}