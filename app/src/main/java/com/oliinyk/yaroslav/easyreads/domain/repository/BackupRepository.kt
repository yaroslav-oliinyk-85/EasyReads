package com.oliinyk.yaroslav.easyreads.domain.repository

import com.oliinyk.yaroslav.easyreads.domain.model.BackupData

interface BackupRepository {
    suspend fun create(): BackupData

    suspend fun save(backupData: BackupData)
}
