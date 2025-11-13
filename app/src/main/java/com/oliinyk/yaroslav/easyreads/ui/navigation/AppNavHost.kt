package com.oliinyk.yaroslav.easyreads.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.screen.book.add_edit.BookAddEditScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.BookListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.my_library.MyLibraryScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.NoteListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_goal.ReadingGoalScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.list.ReadingSessionListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.reading_session.record.ReadingSessionRecordScreen
import java.util.UUID

private const val TAG = "AppNavHost"

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = AppNavRoutes.MyLibrary.createRoute()
    ) {
        // ----- MyLibrary Route -----
        composable(
            route = AppNavRoutes.MyLibrary.route
        ) {
            MyLibraryScreen(
                navToBookAdd = {
                    navHostController.navigate(route = AppNavRoutes.BookAdd.createRoute())
                },
                navToReadingGoal = {
                    navHostController.navigate(route = AppNavRoutes.ReadingGoal.createRoute())
                },
                navToBookListByShelvesType = { bookShelvesType ->
                    navHostController.navigate(route = AppNavRoutes.BookListShelf.createRoute(bookShelvesType))
                },
                navToBookList = {
                    navHostController.navigate(route = AppNavRoutes.BookList.createRoute())
                }
            )
        }

        // ----- ReadingGoal Route -----
        composable(
            route = AppNavRoutes.ReadingGoal.route
        ) {
            ReadingGoalScreen(
                navToBookDetails = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
                }
            )
        }

        // ----- BookListShelf Route -----
        composable(
            route = AppNavRoutes.BookListShelf.route
        ) { backStackEntry ->
            val bookShelvesType = backStackEntry.arguments?.getString(AppNavRoutes.BookListShelf.ARGUMENT_KEY)

            BookListRoute(navHostController, bookShelvesType)
        }

        // ----- BookList Route -----
        composable(
            route = AppNavRoutes.BookList.route
        ) {
            BookListRoute(navHostController)
        }

        // ----- BookDetails Route -----
        composable(
            route = AppNavRoutes.BookDetails.route
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.BookDetails.ARGUMENT_KEY)

            BookDetailsScreen(
                bookId = UUID.fromString(bookId),
                navBack = {
                    navHostController.popBackStack()
                },
                navToBookEdit = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.BookEdit.createRoute(bookId))
                },
                navToReadingSessionRecord = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.ReadingSessionRecord.createRoute(bookId))
                },
                navToReadingSessionList = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.ReadingSessionList.createRoute(bookId))
                },
                navToNoteList = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.NoteList.createRoute(bookId))
                },
            )
        }

        // ----- BookAdd Route -----
        composable(
            route = AppNavRoutes.BookAdd.route
        ) {
            BookAddEditScreen(
                navBack = {
                    navHostController.popBackStack()
                }
            )
        }

        // ----- BookEdit Route -----
        composable(
            route = AppNavRoutes.BookEdit.route
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.BookEdit.ARGUMENT_KEY)

            BookAddEditScreen(
                navBack = {
                    navHostController.popBackStack()
                },
                bookId = bookId
            )
        }

        // ----- NoteList Route -----
        composable(
            route = AppNavRoutes.NoteList.route
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.NoteList.ARGUMENT_KEY)

            NoteListScreen(bookId)
        }

        // ----- ReadingSessionList Route -----
        composable(
            route = AppNavRoutes.ReadingSessionList.route
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.ReadingSessionList.ARGUMENT_KEY)

            ReadingSessionListScreen(bookId)
        }

        // ----- ReadingSessionRecord Route -----
        composable(
            route = AppNavRoutes.ReadingSessionRecord.route
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.ReadingSessionRecord.ARGUMENT_KEY)

            ReadingSessionRecordScreen(
                bookId = bookId,
                navBack = {
                    navHostController.popBackStack()
                },
                navToNoteList = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.NoteList.createRoute(bookId))
                }
            )
        }
    }
}

@Composable
private fun BookListRoute(
    navHostController: NavHostController,
    bookShelvesType: String? = null
) {
    BookListScreen(
        navToBookAdd = {
            navHostController.navigate(route = AppNavRoutes.BookAdd.createRoute())
        },
        navToBookDetails = { bookId ->
            navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
        },
        bookShelvesType = if (bookShelvesType != null) {
            try {
                BookShelvesType.valueOf(bookShelvesType)
            } catch (e: Exception) {
                Log.w(TAG, e.toString())
                null
            }
        } else {
            null
        }
    )
}
