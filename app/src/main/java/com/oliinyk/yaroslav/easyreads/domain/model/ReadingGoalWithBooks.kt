package com.oliinyk.yaroslav.easyreads.domain.model

data class ReadingGoalWithBooks(
    val readingGoal: ReadingGoal,
    val books: List<Book>,
)
