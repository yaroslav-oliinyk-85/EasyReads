package com.oliinyk.yaroslav.easyreads.domain.exception

sealed class ExportImportBackupException : Throwable() {
    data class WriteToFileExportException(
        val errorMessage: String,
    ) : ExportImportBackupException()

    data class NotFoundBackupFileImportException(
        val errorMessage: String,
    ) : ExportImportBackupException()

    data class ReadFromFileImportException(
        val errorMessage: String,
    ) : ExportImportBackupException()

    data class UnsupportedVersionImportException(
        val errorMessage: String,
    ) : ExportImportBackupException()

    data class ValidationBackupImportException(
        val errorMessage: String,
    ) : ExportImportBackupException()
}
