package com.oliinyk.yaroslav.easyreads.ui.navigation

sealed class AppNavRoutes(
    val route: String,
) {
    object MyLibrary : AppNavRoutes("MyLibrary") {
        fun createRoute(): String = route
    }

    object ReadingGoal : AppNavRoutes("ReadingGoal") {
        fun createRoute(): String = route
    }

    object BookListShelf : AppNavRoutes("BookListShelf/{bookSelvesType}") {
        const val ARGUMENT_KEY = "bookSelvesType"

        fun createRoute(bookShelvesType: String): String = "BookListShelf/$bookShelvesType"
    }

    object BookList : AppNavRoutes("BookList") {
        fun createRoute(): String = route
    }

    object BookDetails : AppNavRoutes("BookDetails/{bookId}") {
        const val ARGUMENT_KEY = "bookId"

        fun createRoute(bookId: String): String = "BookDetails/$bookId"
    }

    object BookAdd : AppNavRoutes("BookAdd") {
        fun createRoute(): String = route
    }

    object BookEdit : AppNavRoutes("BookEdit/{bookId}") {
        const val ARGUMENT_KEY = "bookId"

        fun createRoute(bookId: String): String = "BookEdit/$bookId"
    }

    object NoteList : AppNavRoutes("NoteList/{bookId}") {
        const val ARGUMENT_KEY = "bookId"

        fun createRoute(bookId: String): String = "NoteList/$bookId"
    }

    object ReadingSessionList : AppNavRoutes("ReadingSessionList/{bookId}") {
        const val ARGUMENT_KEY = "bookId"

        fun createRoute(bookId: String): String = "ReadingSessionList/$bookId"
    }

    object ReadingSessionRecord : AppNavRoutes("ReadingSessionRecord/{bookId}") {
        const val ARGUMENT_KEY = "bookId"

        fun createRoute(bookId: String): String = "ReadingSessionRecord/$bookId"
    }

    object Settings : AppNavRoutes("Settings") {
        fun createRoute(): String = route
    }
}
