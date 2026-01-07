package com.oliinyk.yaroslav.easyreads.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val version: Int,
    val exportedAtEpochMillis: Long,
    val books: List<Book>,
    val notes: List<Note>,
    val readingSessions: List<ReadingSession>,
    val readingGoals: List<ReadingGoal>,
)
