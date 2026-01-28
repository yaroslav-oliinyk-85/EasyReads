package com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.repository.BookRepository
import com.oliinyk.yaroslav.easyreads.domain.util.deleteBookCoverImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookAddEditViewModel
    @Inject
    constructor(
        private val bookRepository: BookRepository,
    ) : ViewModel() {
        private val tag: String = "BookAddEditViewModel"

        private val _uiState: MutableStateFlow<BookAddEditUiState> =
            MutableStateFlow(
                BookAddEditUiState(),
            )
        val uiState: StateFlow<BookAddEditUiState> = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                bookRepository.getAllAsFlow().collectLatest { books ->
                    _uiState.update {
                        it.copy(
                            suggestionTitles = books.map { book -> book.title }.toList(),
                            suggestionAuthors = books.map { book -> book.author }.distinct(),
                        )
                    }
                }
            }
        }

        fun setup(bookId: String) {
            loadBookById(bookId)
        }

        private fun loadBookById(bookId: String) {
            viewModelScope.launch {
                bookRepository.getById(UUID.fromString(bookId)).collect { book ->
                    _uiState.update { it.copy(book = book ?: Book()) }
                }
            }
        }

        fun updateStateUi(onUpdate: (BookAddEditUiState) -> BookAddEditUiState) {
            _uiState.update { onUpdate(it) }
        }

        fun onEvent(event: BookAddEditEvent) {
            when (event) {
                is BookAddEditEvent.TitleChanged ->
                    updateStateUi {
                        it.copy(book = it.book.copy(title = event.value))
                    }

                is BookAddEditEvent.AuthorChanged ->
                    updateStateUi {
                        it.copy(book = it.book.copy(author = event.value))
                    }

                is BookAddEditEvent.PageAmountChanged ->
                    if (event.value.isBlank()) {
                        updateStateUi { it.copy(book = it.book.copy(pagesCount = 0)) }
                    } else if (event.value.isDigitsOnly()) {
                        updateStateUi { it.copy(book = it.book.copy(pagesCount = event.value.toInt())) }
                    }

                is BookAddEditEvent.IsbnChanged ->
                    if (event.value.isDigitsOnly()) {
                        updateStateUi {
                            it.copy(book = it.book.copy(isbn = event.value))
                        }
                    }

                is BookAddEditEvent.DescriptionChanged ->
                    updateStateUi {
                        it.copy(book = it.book.copy(description = event.value))
                    }

                is BookAddEditEvent.ShelveChanged ->
                    updateStateUi {
                        it.copy(book = updateShelf(it.book.copy(shelf = event.value)))
                    }
            }
        }

        private fun updateShelf(book: Book): Book =
            if (!book.isFinished && book.shelf == BookShelvesType.FINISHED) {
                book.copy(isFinished = true, finishedAt = LocalDateTime.now())
            } else if (book.isFinished && book.shelf != BookShelvesType.FINISHED) {
                book.copy(isFinished = false, finishedAt = null)
            } else {
                book
            }

        fun save(contextApplication: Context) {
            var saveBook = _uiState.value.book
            if (_uiState.value.isNewImageCopied) {
                deleteBookCoverImage(contextApplication, saveBook.coverImageFileName)
                saveBook = saveBook.copy(coverImageFileName = _uiState.value.pickedImageName)
            } else {
                deleteBookCoverImage(contextApplication, _uiState.value.pickedImageName)
            }

            bookRepository.save(saveBook)
        }

        suspend fun updateCoverImage(
            applicationContext: Context,
            pickedImageUri: Uri,
        ) {
            _uiState.value.pickedImageName?.let {
                deleteBookCoverImage(applicationContext, it)
            }
            val pickedImageName = "IMG_${UUID.randomUUID()}.JPG"
            try {
                copyImageToAppFolder(applicationContext, pickedImageUri, pickedImageName)
                _uiState.update {
                    it.copy(isNewImageCopied = true, pickedImageName = pickedImageName)
                }
            } catch (e: IOException) {
                Log.w(tag, "Was not able to book cove image to app folder: $e")
            }
        }

        private suspend fun copyImageToAppFolder(
            applicationContext: Context,
            pickedImageUri: Uri,
            pickedImageName: String,
        ) {
            val destinationFile = File(applicationContext.filesDir, pickedImageName)
            applicationContext.contentResolver.openInputStream(pickedImageUri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }

        fun removeUnusedCoverImage(applicationContext: Context) {
            if (_uiState.value.isNewImageCopied) {
                deleteBookCoverImage(
                    applicationContext,
                    _uiState.value.pickedImageName,
                )
                _uiState.update { it.copy(isNewImageCopied = false) }
            }
        }
    }

data class BookAddEditUiState(
    val book: Book = Book(),
    val pickedImageName: String? = null,
    val tookPhotoName: String? = null,
    val isNewImageCopied: Boolean = false,
    val suggestionTitles: List<String> = emptyList(),
    val suggestionAuthors: List<String> = emptyList(),
)

sealed interface BookAddEditEvent {
    data class ShelveChanged(
        val value: BookShelvesType,
    ) : BookAddEditEvent

    data class TitleChanged(
        val value: String,
    ) : BookAddEditEvent

    data class AuthorChanged(
        val value: String,
    ) : BookAddEditEvent

    data class PageAmountChanged(
        val value: String,
    ) : BookAddEditEvent

    data class IsbnChanged(
        val value: String,
    ) : BookAddEditEvent

    data class DescriptionChanged(
        val value: String,
    ) : BookAddEditEvent
}
