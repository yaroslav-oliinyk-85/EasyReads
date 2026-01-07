package com.oliinyk.yaroslav.easyreads.data.repository

import com.oliinyk.yaroslav.easyreads.data.local.dao.ReadingGoalDao
import com.oliinyk.yaroslav.easyreads.data.local.entety.toModel
import com.oliinyk.yaroslav.easyreads.di.AppCoroutineScope
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.domain.model.toEntity
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingGoalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadingGoalRepositoryImpl
    @Inject
    constructor(
        private val readingGoalDao: ReadingGoalDao,
        @AppCoroutineScope private val coroutineScope: CoroutineScope,
        @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    ) : ReadingGoalRepository {
        override suspend fun getAll(): List<ReadingGoal> =
            readingGoalDao
                .getAll()
                .map { entity -> entity.toModel() }

        override fun getByYear(year: Int): Flow<ReadingGoal?> =
            readingGoalDao
                .getByYear(year)
                .map { it?.toModel() }
                .distinctUntilChanged()

        override fun save(readingGoal: ReadingGoal) {
            coroutineScope.launch(ioDispatcher) {
                readingGoalDao.insert(readingGoal.toEntity())
            }
        }

        override fun saveAll(readingGoals: List<ReadingGoal>) {
            coroutineScope.launch(ioDispatcher) {
                readingGoalDao.upsertAll(readingGoals.map { it.toEntity() })
            }
        }

        override fun update(readingGoal: ReadingGoal) {
            coroutineScope.launch(ioDispatcher) {
                readingGoalDao.update(readingGoal.toEntity())
            }
        }
    }
