package de.tobsinger.scoreboard.core.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import de.tobsinger.scoreboard.core.ui.navigation.NavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScoreboardUi() {

    val navHostController: NavHostController = rememberAnimatedNavController()
    NavHost(
        navHostController = navHostController,
    )
}
