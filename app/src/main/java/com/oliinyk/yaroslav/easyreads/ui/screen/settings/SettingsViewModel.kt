package com.oliinyk.yaroslav.easyreads.ui.screen.settings

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.R
import com.oliinyk.yaroslav.easyreads.domain.exception.ExportImportBackupException
import com.oliinyk.yaroslav.easyreads.domain.usecase.ExportBackupDataUseCase
import com.oliinyk.yaroslav.easyreads.domain.usecase.ImportBackupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingsViewModel"

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val exportBackupDataUseCase: ExportBackupDataUseCase,
        private val importBackupDataUseCase: ImportBackupDataUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())

        val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

        fun exportBackupData(
            context: Context,
            uri: Uri,
        ) {
            if (!_uiState.value.isBackupInProgress) {
                _uiState.update { it.copy(isBackupInProgress = true) }

                viewModelScope.launch {
                    try {
                        exportBackupDataUseCase(context, uri)
                    } catch (e: ExportImportBackupException) {
                        Log.e(TAG, e.message ?: e.toString())
                        _uiState.update {
                            it.copy(backupErrorMessageId = R.string.export_data_dialog__label__error_message_text)
                        }
                    }

                    _uiState.update { it.copy(isBackupInProgress = false) }
                }
            }
        }

        fun importBackupData(
            context: Context,
            uri: Uri,
        ) {
            if (!_uiState.value.isBackupInProgress) {
                _uiState.update { it.copy(isBackupInProgress = true) }

                viewModelScope.launch {
                    try {
                        importBackupDataUseCase(context, uri)
                    } catch (e: ExportImportBackupException) {
                        Log.e(TAG, e.message ?: e.toString())
                        _uiState.update {
                            it.copy(backupErrorMessageId = R.string.export_data_dialog__label__error_message_text)
                        }
                    }

                    _uiState.update { it.copy(isBackupInProgress = false) }
                }
            }
        }

        fun clearBackupErrorMessage() = _uiState.update { it.copy(backupErrorMessageId = null) }
    }

data class SettingsUiState(
    val isBackupInProgress: Boolean = false,
    @StringRes
    val backupErrorMessageId: Int? = null,
)
