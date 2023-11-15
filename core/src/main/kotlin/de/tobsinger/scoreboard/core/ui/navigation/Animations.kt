package de.tobsinger.scoreboard.core.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOutLeft() =
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(),
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideOutRight() =
    slideOutOfContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(),
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideInLeft() =
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(),
    )

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<NavBackStackEntry>.slideInRight() =
    slideIntoContainer(
        AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(),
    )

