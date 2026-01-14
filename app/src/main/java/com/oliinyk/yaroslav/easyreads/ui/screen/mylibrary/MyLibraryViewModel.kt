package com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingGoal
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.ReadingGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
        private val readingGoalRepository: ReadingGoalRepository,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<MyLibraryUiState> =
            MutableStateFlow(MyLibraryUiState())
        val uiState: StateFlow<MyLibraryUiState>
            get() = _uiState.asStateFlow()

        init {
            loadReadingGoal()
            loadBooks()
        }

        private fun loadReadingGoal() {
            viewModelScope.launch {
                val currentYear: Int = LocalDate.now().year
                readingGoalRepository.getByYear(currentYear).collectLatest { readingGoal ->
                    if (readingGoal != null) {
                        _uiState.update { it.copy(readingGoal = readingGoal) }
                    } else {
                        readingGoalRepository.save(ReadingGoal())
                    }
                }
            }
        }

        private fun loadBooks() {
            viewModelScope.launch {
                bookRepository.getAllAsFlow().collectLatest { books ->
                    val currentYearFinishedBooks: List<Book> =
                        books.filter {
                            it.isFinished && (it.finishedAt != null) && (it.finishedAt.year == LocalDate.now().year)
                        }
                    _uiState.update {
                        it.copy(
                            finishedBooksCount = books.count { book -> book.shelf == BookShelvesType.FINISHED },
                            readingBooksCount = books.count { book -> book.shelf == BookShelvesType.READING },
                            wantToReadBooksCount = books.count { book -> book.shelf == BookShelvesType.WANT_TO_READ },
                            totalBooksCount = books.size,
                            currentYearFinishedBooksCount = currentYearFinishedBooks.size,
                        )
                    }
                }
            }
        }

        fun updateReadingGoal(readingGoal: ReadingGoal) {
            readingGoalRepository.update(readingGoal)
        }
    }

data class MyLibraryUiState(
    val finishedBooksCount: Int = 0,
    val readingBooksCount: Int = 0,
    val wantToReadBooksCount: Int = 0,
    val totalBooksCount: Int = 0,
    val currentYearFinishedBooksCount: Int = 0,
    val readingGoal: ReadingGoal = ReadingGoal(),
)
