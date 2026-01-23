package com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoalWithBooks
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingGoalRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingSessionRepository
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MILLISECONDS_IN_ONE_MINUTE
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MINUTES_IN_ONE_HOUR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.math.roundToInt

@HiltViewModel
class ReadingGoalViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
        private val readingGoalRepository: ReadingGoalRepository,
        private val readingSessionRepository: ReadingSessionRepository,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<ReadingGoalUiState> =
            MutableStateFlow(ReadingGoalUiState())
        val uiState: StateFlow<ReadingGoalUiState>
            get() = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                loadReadingGoalsWithBooks()
            }
        }

        private suspend fun loadReadingGoalsWithBooks() {
            readingGoalRepository
                .getAllAsFlow()
                .combine(bookRepository.getAllAsFlow()) { readingGoals, books ->
                    if (readingGoals.isEmpty()) {
                        readingGoalRepository.save(ReadingGoal(year = LocalDate.now().year))
                        emptyMap()
                    } else {
                        val readingGoalsWithBooks: MutableMap<Int, ReadingGoalWithBooks> = mutableMapOf()
                        readingGoals.forEach { goal ->
                            readingGoalsWithBooks[goal.year] =
                                ReadingGoalWithBooks(
                                    readingGoal = goal,
                                    books =
                                        books
                                            .filter {
                                                it.isFinished &&
                                                    (it.finishedAt != null) &&
                                                    (it.finishedAt.year == goal.year)
                                            }.sortedByDescending { it.finishedAt },
                                )
                        }
                        readingGoalsWithBooks.toMap()
                    }
                }.flowOn(Dispatchers.IO)
                .collectLatest { readingGoalsWithBooks ->
                    _uiState.update {
                        it.copy(
                            readingGoalsWithBooks = readingGoalsWithBooks,
                            oldestYear = readingGoalsWithBooks.keys.min(),
                        )
                    }
                    updateStatistic(_uiState.value.selectedReadingGoal.year)
                }
        }

        private suspend fun updateStatistic(year: Int) {
            val readingGoalWithBooks = _uiState.value.readingGoalsWithBooks[year]
            readingGoalWithBooks?.let { goalBooks ->
                val finishedBooks = goalBooks.books
                val readPages =
                    if (finishedBooks.isNotEmpty()) {
                        finishedBooks
                            .map { it.pageAmount }
                            .reduce { sum, pages -> sum + pages }
                    } else {
                        0
                    }

                _uiState.update { state ->
                    state.copy(
                        selectedReadingGoal = goalBooks.readingGoal,
                        finishedBooks = finishedBooks,
                        finishedBooksCount = finishedBooks.size,
                        readPages = readPages,
                    )
                }
                loadReadingSession(finishedBooks)
            }
        }

        private suspend fun loadReadingSession(finishedBooks: List<Book>) {
            val sessions =
                readingSessionRepository.getAllByBookIds(
                    finishedBooks.map { it.id }.toList(),
                )
            if (sessions.isNotEmpty()) {
                val totalReadTimeInMilliseconds =
                    sessions
                        .map { it.readTimeInMilliseconds }
                        .reduce { acc, value -> acc + value }
                val totalReadMinutes = totalReadTimeInMilliseconds / MILLISECONDS_IN_ONE_MINUTE

                _uiState.update { state ->
                    state.copy(
                        averagePagesHour =
                            (
                                state.readPages.toDouble() / totalReadMinutes *
                                    MINUTES_IN_ONE_HOUR
                            ).roundToInt(),
                        readHours = (totalReadMinutes / MINUTES_IN_ONE_HOUR).toInt(),
                        readMinutes = (totalReadMinutes % MINUTES_IN_ONE_HOUR).toInt(),
                    )
                }
            }
        }

        fun updateReadingGoal(readingGoal: ReadingGoal) {
            if (_uiState.value.selectedReadingGoal.goal != readingGoal.goal) {
                readingGoalRepository.update(readingGoal)
            }
        }

        fun updateSelectedReading(year: Int) {
            viewModelScope.launch {
                updateStatistic(year)
            }
        }
    }

data class ReadingGoalUiState(
    val readingGoalsWithBooks: Map<Int, ReadingGoalWithBooks> = emptyMap(),
    val selectedReadingGoal: ReadingGoal = ReadingGoal(),
    val finishedBooks: List<Book> = emptyList(),
    val finishedBooksCount: Int = 0,
    val readPages: Int = 0,
    val averagePagesHour: Int = 0,
    val readHours: Int = 0,
    val readMinutes: Int = 0,
    val oldestYear: Int = LocalDate.now().year,
)
