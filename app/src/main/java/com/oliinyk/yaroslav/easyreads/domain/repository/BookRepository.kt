package com.oliinyk.yaroslav.easyreads.domain.repository

import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.BookSorting
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface BookRepository {
    fun getAllSorted(bookSorting: BookSorting): Flow<List<Book>>

    fun getByShelveSorted(
        bookShelvesType: BookShelvesType,
        bookSorting: BookSorting,
    ): Flow<List<Book>>

    suspend fun getAll(): List<Book>

    fun getAllAsFlow(): Flow<List<Book>>

    fun getById(id: UUID): Flow<Book?>

    fun getAuthors(): Flow<List<String>>

    fun save(book: Book)

    fun saveAll(books: List<Book>)

    fun update(book: Book)

    fun remove(book: Book)
}
