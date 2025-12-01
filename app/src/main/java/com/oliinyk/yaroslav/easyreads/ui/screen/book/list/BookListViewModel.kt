package com.oliinyk.yaroslav.easyreads.ui.screen.book.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSorting
import com.oliinyk.yaroslav.easyreads.domain.model.HolderSize
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
        private val preferencesRepository: PreferencesRepository,
    ) : ViewModel() {
        private val _stateUi: MutableStateFlow<StateUiBookList> = MutableStateFlow(StateUiBookList())
        val stateUi: StateFlow<StateUiBookList>
            get() = _stateUi.asStateFlow()

        val bookSorting: BookSorting
            get() = stateUi.value.bookSorting

        val bookShelvesType: BookShelvesType?
            get() = stateUi.value.bookShelvesType

        private var jobFetchBooks: Job? = null

        init {
            viewModelScope.launch {
                preferencesRepository.getBookListCellHolderSize().collectLatest { holderSizeString ->
                    if (holderSizeString.isNotEmpty()) {
                        _stateUi.update {
                            it.copy(
                                holderSize = HolderSize.valueOf(holderSizeString),
                            )
                        }
                    }
                }
            }
            viewModelScope.launch {
                preferencesRepository.getBookSorting().collectLatest { bookSortingString ->
                    if (bookSortingString.isNotEmpty()) {
                        val bookSorting = BookSorting.fromString(bookSortingString)
                        _stateUi.update { it.copy(bookSorting = bookSorting) }
                    }
                    loadBooks()
                }
            }
        }

        fun updateBookSorting(bookSorting: BookSorting) {
            viewModelScope.launch {
                preferencesRepository.setBookSorting(bookSorting.toString())
            }
        }

        fun updateHolderSize(holderSize: HolderSize) {
            viewModelScope.launch {
                preferencesRepository.setBookListCellHolderSize(holderSize.toString())
            }
        }

        fun updateBookShelveType(updatedBookShelvesType: BookShelvesType) {
            _stateUi.update { it.copy(bookShelvesType = updatedBookShelvesType) }
            loadBooks()
        }

        private fun loadBooks() {
            jobFetchBooks?.cancel()
            jobFetchBooks =
                viewModelScope.launch {
                    if (bookShelvesType != null) {
                        bookRepository.getByShelveSorted(bookShelvesType!!, bookSorting).collect { books ->
                            _stateUi.update { it.copy(books = books) }
                        }
                    } else {
                        bookRepository.getAllSorted(bookSorting).collect { books ->
                            _stateUi.update { it.copy(books = books) }
                        }
                    }
                }
        }
    }

data class StateUiBookList(
    val books: List<Book> = emptyList(),
    val holderSize: HolderSize = HolderSize.DEFAULT,
    val bookSorting: BookSorting = BookSorting(),
    val bookShelvesType: BookShelvesType? = null,
)
