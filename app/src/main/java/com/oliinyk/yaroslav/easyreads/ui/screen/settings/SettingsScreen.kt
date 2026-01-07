package com.oliinyk.yaroslav.easyreads.ui.screen.settings

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.model.BackupData
import com.oliinyk.yaroslav.easyreads.domain.repository.BackupRepository
import com.oliinyk.yaroslav.easyreads.domain.usecase.ExportBackupDataUseCase
import com.oliinyk.yaroslav.easyreads.domain.usecase.ImportBackupDataUseCase
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants
import com.oliinyk.yaroslav.easyreads.ui.components.AppTextButton
import com.oliinyk.yaroslav.easyreads.ui.screen.settings.components.SettingsContent
import com.oliinyk.yaroslav.easyreads.ui.screen.settings.components.SettingsTopAppBar
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens
import com.oliinyk.yaroslav.easyreads.ui.theme.EasyReadsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var openExportDialog by rememberSaveable { mutableStateOf(false) }
    var openImportDialog by rememberSaveable { mutableStateOf(false) }

    if (openExportDialog) {
        ExportDataDialog(
            uiState = uiState,
            onClickOK = {
                openExportDialog = false
                viewModel.clearBackupErrorMessage()
            },
        )
    }

    if (openImportDialog) {
        ImportDataDialog(
            uiState = uiState,
            onClickOK = {
                openImportDialog = false
                clearCompositionErrors()
            },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsTopAppBar()
        },
        content = { paddingValues ->
            val context = LocalContext.current

            val exportBackupLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.CreateDocument(AppConstants.MIME_TYPE_APPLICATION_ZIP),
                ) { resultUri ->
                    resultUri?.let { uri ->
                        openExportDialog = true
                        viewModel.exportBackupData(context, uri)
                    }
                }
            val importBackupLauncher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                ) { resultUri ->
                    resultUri?.let { uri ->
                        openImportDialog = true
                        viewModel.importBackupData(context, uri)
                    }
                }

            val dateTimePattern = stringResource(R.string.date_year_month_day_and_time_format)
            SettingsContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                onClickExport = {
                    if (!uiState.isBackupInProgress) {
                        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern))
                        val backupDataFileName = "EasyReadsBackup_$timestamp.zip"
                        exportBackupLauncher.launch(backupDataFileName)
                    }
                },
                onClickImport = {
                    if (!uiState.isBackupInProgress) {
                        importBackupLauncher.launch(arrayOf(AppConstants.MIME_TYPE_APPLICATION_ZIP))
                    }
                },
            )
        },
    )
}

@Composable
private fun ExportDataDialog(
    uiState: SettingsUiState,
    onClickOK: () -> Unit,
) {
    ExportImportDataDialog(
        title = stringResource(R.string.export_data_dialog__label__title_text),
        message = stringResource(R.string.export_data_dialog__label__message_text),
        uiState = uiState,
        onClickOK = onClickOK,
    )
}

@Composable
private fun ImportDataDialog(
    uiState: SettingsUiState,
    onClickOK: () -> Unit,
) {
    ExportImportDataDialog(
        title = stringResource(R.string.import_data_dialog__label__title_text),
        message = stringResource(R.string.import_data_dialog__label__message_text),
        uiState = uiState,
        onClickOK = onClickOK,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExportImportDataDialog(
    title: String,
    message: String,
    uiState: SettingsUiState,
    onClickOK: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = title,
                color =
                    if (uiState.backupErrorMessageId == null) {
                        Color.Unspecified
                    } else {
                        MaterialTheme.colorScheme.error
                    },
            )
        },
        text = {
            Crossfade(
                targetState = uiState.isBackupInProgress,
            ) { visible ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    when (visible) {
                        true -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        false ->
                            Text(
                                text = uiState.backupErrorMessageId?.let { stringResource(it) } ?: message,
                                maxLines = Dimens.confirmDialogMessageMaxLine,
                                overflow = TextOverflow.Ellipsis,
                                color =
                                    if (uiState.backupErrorMessageId == null) {
                                        Color.Unspecified
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    },
                            )
                    }
                }
            }
        },
        confirmButton = {
            AnimatedVisibility(!uiState.isBackupInProgress) {
                AppTextButton(
                    onClick = {
                        onClickOK()
                    },
                ) {
                    Text(
                        text = stringResource(R.string.confirmation_dialog__button__ok_text),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        },
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun SettingsScreenPreview() {
    EasyReadsTheme {
        SettingsScreen(
            viewModel =
                SettingsViewModel(
                    ExportBackupDataUseCase(
                        backupRepository =
                            object : BackupRepository {
                                override suspend fun create(): BackupData =
                                    BackupData(
                                        version = 1,
                                        exportedAtEpochMillis = 0,
                                        books = emptyList(),
                                        notes = emptyList(),
                                        readingSessions = emptyList(),
                                        readingGoals = emptyList(),
                                    )

                                override suspend fun save(backupData: BackupData) {
                                    // preview
                                }
                            },
                        json = Json,
                        ioDispatcher = Dispatchers.IO,
                    ),
                    ImportBackupDataUseCase(
                        backupRepository =
                            object : BackupRepository {
                                override suspend fun create(): BackupData =
                                    BackupData(
                                        version = 1,
                                        exportedAtEpochMillis = 0,
                                        books = emptyList(),
                                        notes = emptyList(),
                                        readingSessions = emptyList(),
                                        readingGoals = emptyList(),
                                    )

                                override suspend fun save(backupData: BackupData) {
                                    // preview
                                }
                            },
                        json = Json,
                        ioDispatcher = Dispatchers.IO,
                    ),
                ),
        )
    }
}

@Preview
@Composable
private fun ExportDataDialogPreviewInProgress() {
    EasyReadsTheme {
        ExportDataDialog(
            uiState = SettingsUiState().copy(isBackupInProgress = true),
            onClickOK = {},
        )
    }
}

@Preview
@Composable
private fun ExportDataDialogPreviewExportFinished() {
    EasyReadsTheme {
        ExportDataDialog(
            uiState = SettingsUiState(),
            onClickOK = {},
        )
    }
}

@Preview
@Composable
private fun ImportDataDialogPreviewInProgress() {
    EasyReadsTheme {
        ImportDataDialog(
            uiState = SettingsUiState().copy(isBackupInProgress = true),
            onClickOK = {},
        )
    }
}

@Preview
@Composable
private fun ImportDataDialogPreviewExportFinished() {
    EasyReadsTheme {
        ImportDataDialog(
            uiState = SettingsUiState(),
            onClickOK = {},
        )
    }
}

@Preview
@Composable
private fun ImportDataDialogPreviewExportFailed() {
    EasyReadsTheme {
        ImportDataDialog(
            uiState = SettingsUiState(backupErrorMessageId = R.string.import_data_dialog__label__error_message_text),
            onClickOK = {},
        )
    }
}
