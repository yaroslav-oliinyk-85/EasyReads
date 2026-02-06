package com.oliinyk.yaroslav.easyreads.ui.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
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
import com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.record.ReadingSessionRecordScreen
import com.oliinyk.yaroslav.easyreads.ui.screen.settings.SettingsScreen
import java.util.UUID

private const val TAG = "AppNavHost"

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppNavRoutes.MyLibrary.createRoute(),
    ) {
        // ----- MyLibrary Route -----
        composable(
            route = AppNavRoutes.MyLibrary.route,
            enterTransition = AppNavTransitions.myLibraryEnterTransition,
            exitTransition = AppNavTransitions.exitTransition,
            popEnterTransition = AppNavTransitions.popEnterTransition,
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
                },
                navToSettings = {
                    navHostController.navigate(route = AppNavRoutes.Settings.createRoute())
                },
            )
        }

        // ----- ReadingGoal Route -----
        appComposable(
            route = AppNavRoutes.ReadingGoal.route,
        ) {
            ReadingGoalScreen(
                navBack = {
                    navHostController.popBackStack()
                },
                navToBookDetails = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
                },
            )
        }

        // ----- BookListShelf Route -----
        appComposable(
            route = AppNavRoutes.BookListShelf.route,
        ) { backStackEntry ->
            val bookShelvesType = backStackEntry.arguments?.getString(AppNavRoutes.BookListShelf.ARGUMENT_KEY)

            BookListRoute(navHostController, bookShelvesType)
        }

        // ----- BookList Route -----
        appComposable(
            route = AppNavRoutes.BookList.route,
        ) {
            BookListRoute(navHostController)
        }

        // ----- BookDetails Route -----
        appComposable(
            route = AppNavRoutes.BookDetails.route,
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
            )
        }

        // ----- BookAdd Route -----
        appComposable(
            route = AppNavRoutes.BookAdd.route,
        ) {
            BookAddEditScreen(
                navBack = {
                    navHostController.popBackStack()
                },
                navToBookDetails = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
                },
            )
        }

        // ----- BookEdit Route -----
        appComposable(
            route = AppNavRoutes.BookEdit.route,
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.BookEdit.ARGUMENT_KEY)

            BookAddEditScreen(
                navBack = {
                    navHostController.popBackStack()
                },
                bookId = bookId,
            )
        }

        // ----- NoteList Route -----
        appComposable(
            route = AppNavRoutes.NoteList.route,
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(AppNavRoutes.NoteList.ARGUMENT_KEY)

            NoteListScreen(
                bookId = bookId,
                navBack = {
                    navHostController.popBackStack()
                },
            )
        }

        // ----- ReadingSessionRecord Route -----
        appComposable(
            route = AppNavRoutes.ReadingSessionRecord.route,
        ) { backStackEntry ->
            val bookIdString = backStackEntry.arguments?.getString(AppNavRoutes.ReadingSessionRecord.ARGUMENT_KEY)

            ReadingSessionRecordScreen(
                bookId = bookIdString,
                navBack = {
                    navHostController.popBackStack()
                },
                navToNoteList = { bookId ->
                    navHostController.navigate(route = AppNavRoutes.NoteList.createRoute(bookId))
                },
            )
        }

        // ----- Settings Route -----
        appComposable(
            route = AppNavRoutes.Settings.route,
        ) {
            SettingsScreen(
                navBack = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}

private fun NavGraphBuilder.appComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        enterTransition = AppNavTransitions.enterTransition,
        exitTransition = AppNavTransitions.exitTransition,
        popEnterTransition = AppNavTransitions.popEnterTransition,
        popExitTransition = AppNavTransitions.popExitTransition,
        content = content,
    )
}

@Composable
private fun BookListRoute(
    navHostController: NavHostController,
    bookShelvesType: String? = null,
) {
    BookListScreen(
        navBack = {
            navHostController.popBackStack()
        },
        navToBookAdd = {
            navHostController.navigate(route = AppNavRoutes.BookAdd.createRoute())
        },
        navToBookDetails = { bookId ->
            navHostController.navigate(route = AppNavRoutes.BookDetails.createRoute(bookId))
        },
        bookShelvesType =
            if (bookShelvesType != null) {
                try {
                    BookShelvesType.valueOf(bookShelvesType)
                } catch (e: IllegalArgumentException) {
                    Log.w(TAG, e.toString())
                    null
                }
            } else {
                null
            },
    )
}
