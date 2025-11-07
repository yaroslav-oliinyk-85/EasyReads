package com.oliinyk.yaroslav.easyreads.presentation.book.add_edit

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
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookAddEditViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val tag: String = "BookAddEditViewModel"

    private val _stateUi: MutableStateFlow<BookAddEditStateUi> = MutableStateFlow(
        BookAddEditStateUi()
    )
    val stateUi: StateFlow<BookAddEditStateUi> = _stateUi.asStateFlow()

    init {
        viewModelScope.launch {
            bookRepository.getAll().collectLatest { books ->
                _stateUi.update {
                    it.copy(
                        suggestionTitles = books.map { book -> book.title }.toList(),
                        suggestionAuthors = books.map { book -> book.author }.distinct()
                    )
                }
            }
        }
    }

    fun updateStateUi(onUpdate: (BookAddEditStateUi) -> BookAddEditStateUi) {
        _stateUi.update { onUpdate(it) }
    }

    fun onEvent(event: BookAddEditEvent) {
        when (event) {
            is BookAddEditEvent.TitleChanged -> updateStateUi {
                it.copy(book = it.book.copy(title = event.value))
            }

            is BookAddEditEvent.AuthorChanged -> updateStateUi {
                it.copy(book = it.book.copy(author = event.value))
            }

            is BookAddEditEvent.PageAmountChanged -> if (event.value.isBlank()) {
                updateStateUi { it.copy(book = it.book.copy(pageAmount = 0)) }
            } else if (event.value.isDigitsOnly()) {
                updateStateUi { it.copy(book = it.book.copy(pageAmount = event.value.toInt())) }
            }

            is BookAddEditEvent.IsbnChanged -> if (event.value.isDigitsOnly()) {
                updateStateUi {
                    it.copy(book = it.book.copy(isbn = event.value))
                }
            }

            is BookAddEditEvent.DescriptionChanged -> updateStateUi {
                it.copy(book = it.book.copy(description = event.value))
            }

            is BookAddEditEvent.ShelveChanged -> updateStateUi {
                it.copy(book = it.book.copy(shelf = event.value))
            }
        }
    }

    fun save(contextApplication: Context) {
        var saveBook = _stateUi.value.book
        if (_stateUi.value.isNewImageCopied) {
            deleteBookCoverImage(contextApplication, saveBook.coverImageFileName)
            saveBook = saveBook.copy(coverImageFileName = _stateUi.value.pickedImageName)
        } else {
            deleteBookCoverImage(contextApplication, _stateUi.value.pickedImageName)
        }
        if (!saveBook.isFinished && saveBook.shelf == BookShelvesType.FINISHED) {
            saveBook = saveBook.copy(isFinished = true, finishedDate = Date())
        } else if (saveBook.isFinished && saveBook.shelf != BookShelvesType.FINISHED) {
            saveBook = saveBook.copy(isFinished = false, finishedDate = null)
        }

        bookRepository.save(saveBook)
    }

    suspend fun updateCoverImage(applicationContext: Context, pickedImageUri: Uri) {
        _stateUi.value.pickedImageName?.let {
            deleteBookCoverImage(applicationContext, it)
        }
        val pickedImageName = "BOOK_COVER_IMG_${UUID.randomUUID()}.JPG"
        try {
            copyImageToAppFolder(applicationContext, pickedImageUri, pickedImageName)
            _stateUi.update {
                it.copy(isNewImageCopied = true, pickedImageName = pickedImageName)
            }
        } catch (e: IOException) {
            Log.w(tag, "Was not able to book cove image to app folder: $e")
        }
    }

    private suspend fun copyImageToAppFolder(
        applicationContext: Context,
        pickedImageUri: Uri,
        pickedImageName: String
    ) {
        val destinationFile = File(applicationContext.filesDir, pickedImageName)
        applicationContext.contentResolver.openInputStream(pickedImageUri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                val buffer = ByteArray(1024 * 8)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
    }

    fun removeUnusedCoverImage(applicationContext: Context) {
        if (_stateUi.value.isNewImageCopied) {
            deleteBookCoverImage(
                applicationContext,
                _stateUi.value.pickedImageName
            )
            _stateUi.update { it.copy(isNewImageCopied = false) }
        }
    }
}

data class BookAddEditStateUi(
    val book: Book = Book(),
    val pickedImageName: String? = null,
    val tookPhotoName: String? = null,
    val isNewImageCopied: Boolean = false,
    val suggestionTitles: List<String> = emptyList(),
    val suggestionAuthors: List<String> = emptyList()
)

sealed interface BookAddEditEvent {
    data class ShelveChanged(val value: BookShelvesType) : BookAddEditEvent
    data class TitleChanged(val value: String) : BookAddEditEvent
    data class AuthorChanged(val value: String) : BookAddEditEvent
    data class PageAmountChanged(val value: String) : BookAddEditEvent
    data class IsbnChanged(val value: String) : BookAddEditEvent
    data class DescriptionChanged(val value: String) : BookAddEditEvent

//    object SaveClicked : BookAddEditEvent
//    object BackClicked : BookAddEditEvent
//    object CoverClicked : BookAddEditEvent
}