package com.oliinyk.yaroslav.easyreads.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme

@Composable
fun SettingsContent(
    onClickExport: () -> Unit,
    onClickImport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(all = Dimens.paddingAllMedium)
                .background(MaterialTheme.colorScheme.background),
    ) {
        SettingsDataSection(
            onClickExport = {
                onClickExport()
            },
            onClickImport = {
                onClickImport()
            },
        )
    }
}

@Composable
private fun SettingsDataSection(
    onClickExport: () -> Unit,
    onClickImport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape =
            RoundedCornerShape(
                Dimens.roundedCornerShapeSize,
            ),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.paddingAllMedium),
        ) {
            AppTextButton(
                onClick = {
                    onClickExport()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.settings__button__export_data_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(Modifier.height(Dimens.spacerHeightSmall))
            AppTextButton(
                onClick = {
                    onClickImport()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.settings__button__import_data_text),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenContentPreview() {
    EasyReadsTheme {
        SettingsContent(
            onClickExport = { },
            onClickImport = { },
        )
    }
}
