package com.oliinyk.yaroslav.easyreads.domain.repository

import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import kotlinx.coroutines.flow.Flow

interface ReadingGoalRepository {
    suspend fun getAll(): List<ReadingGoal>

    fun getAllAsFlow(): Flow<List<ReadingGoal>>

    fun getByYear(year: Int): Flow<ReadingGoal?>

    fun save(readingGoal: ReadingGoal)

    fun saveAll(readingGoals: List<ReadingGoal>)

    fun update(readingGoal: ReadingGoal)
}
