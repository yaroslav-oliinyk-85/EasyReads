package com.oliinyk.yaroslav.easyreads.domain.util

import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.domain.model.ReadingSession
import java.time.LocalDateTime

object BookUtil {
    fun getFinishedWithFinishedAtAndShelf(
        book: Book,
        readingSession: ReadingSession,
    ): Triple<Boolean, LocalDateTime?, BookShelvesType> =
        if (!book.isFinished && book.pagesCount == readingSession.endPage) {
            Triple(true, readingSession.startedAt, BookShelvesType.FINISHED)
        } else {
            Triple(book.isFinished, book.finishedAt, book.shelf)
        }
}
