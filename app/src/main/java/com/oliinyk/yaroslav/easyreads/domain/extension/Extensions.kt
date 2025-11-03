package com.oliinyk.yaroslav.easyreads.domain.extension

import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants

fun String.toBookPage(): Int {
    return this.trim()
        .filter { it.isDigit() }
        .take(AppConstants.BOOK_PAGE_AMOUNT_MAX_LENGTH)
        .toIntOrNull() ?: 0
}

fun String.takeFirstTwoDigits(): Int {
    val length = 2
    return this.trim()
        .filter { it.isDigit() }
        .take(length)
        .toIntOrNull() ?: 0
}