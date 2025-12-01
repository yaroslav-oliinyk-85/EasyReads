package com.oliinyk.yaroslav.easyreads.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import com.oliinyk.yaroslav.easyreads.ui.screen.book.addedit.BookAddEditScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.book.details.BookDetailsScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.book.list.BookListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.mylibrary.MyLibraryScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.note.list.NoteListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.readinggoal.ReadingGoalScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.list.ReadingSessionListScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.ReadingSessionRecordScreen
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
            route = AppNavRoutes.MyLibrary.route,
            enterTransition = AppNavTransitions.myLibraryEnterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition
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
            route = AppNavRoutes.ReadingGoal.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) {
            ReadingGoalScreen(
                navToBookDetails = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
                }
            )
        }

        // ----- BookListShelf Route -----
        composable(
            route = AppNavRoutes.BookListShelf.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) { backStackEntry ->
            val bookShelvesType = backStackEntry.arguments?.getString(AppNavRoutes.BookListShelf.ARGUMENT_KEY)

            BookListRoute(navHostController, bookShelvesType)
        }

        // ----- BookList Route -----
        composable(
            route = AppNavRoutes.BookList.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) {
            BookListRoute(navHostController)
        }

        // ----- BookDetails Route -----
        composable(
            route = AppNavRoutes.BookDetails.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) { backStackEntry ->
            val bookIdString = backStackEntry.arguments?.getString(AppNavRoutes.BookDetails.ARGUMENT_KEY)

            BookDetailsScreen(
                bookId = UUID.fromString(bookIdString),
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
            route = AppNavRoutes.BookAdd.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) {
            BookAddEditScreen(
                navBack = {
                    navHostController.popBackStack()
                }
            )
        }

        // ----- BookEdit Route -----
        composable(
            route = AppNavRoutes.BookEdit.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
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
            route = AppNavRoutes.NoteList.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.NoteList.ARGUMENT_KEY)

            NoteListScreen(bookId)
        }

        // ----- ReadingSessionList Route -----
        composable(
            route = AppNavRoutes.ReadingSessionList.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.ReadingSessionList.ARGUMENT_KEY)

            ReadingSessionListScreen(bookId)
        }

        // ----- ReadingSessionRecord Route -----
        composable(
            route = AppNavRoutes.ReadingSessionRecord.route,
            enterTransition = AppNavTransitions.enterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
            popExitTransition = AppNavTransitions.popExitTransition
        ) { backStackEntry ->
            val bookIdString = backStackEntry.arguments?.getString(AppNavRoutes.ReadingSessionRecord.ARGUMENT_KEY)

            ReadingSessionRecordScreen(
                bookId = bookIdString,
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
