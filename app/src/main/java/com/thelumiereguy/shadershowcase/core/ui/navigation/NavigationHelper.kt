package com.thelumiereguy.shadershowcase.core.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.composableSlide(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1500 }, animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1500 }, animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1500 }, animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1500 }, animationSpec = tween(200)
            )
        },
        content = content
    )
}