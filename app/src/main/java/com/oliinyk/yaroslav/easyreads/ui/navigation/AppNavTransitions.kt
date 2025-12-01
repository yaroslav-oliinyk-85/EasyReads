package com.oliinyk.yaroslav.easyreads.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.oliinyk.yaroslav.easyreads.ui.theme.Dimens

object AppNavTransitions {
    val myLibraryEnterTransition:
        (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
            fadeIn(
                animationSpec = tween(Dimens.animationEnterDurationMillis),
            )
        }

    val enterTransition:
        (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec =
                    tween(
                        durationMillis = Dimens.animationEnterDurationMillis,
                        delayMillis = Dimens.animationDelayDurationMillis,
                    ),
            )
        }

    val exitTransition:
        (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
            fadeOut(
                animationSpec =
                    tween(
                        durationMillis = Dimens.animationExitDurationMillis,
                        delayMillis = Dimens.animationDelayDurationMillis,
                    ),
            )
        }

    val popEnterTransition:
        (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
            fadeIn(
                animationSpec =
                    tween(
                        Dimens.animationPopEnterDurationMillis,
                    ),
            )
        }

    val popExitTransition:
        (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec =
                    tween(
                        Dimens.animationPopExitDurationMillis,
                    ),
            )
        }
}
