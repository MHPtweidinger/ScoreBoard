package de.tobsinger.scoreboard.core.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import de.tobsinger.scoreboard.core.ui.navigation.Params.PARAM_PLAYER_NAME
import de.tobsinger.scoreboard.core.ui.navigation.Routes.ADD_PLAYER
import de.tobsinger.scoreboard.core.ui.navigation.Routes.SCORES
import de.tobsinger.scoreboard.core.ui.navigation.Routes.UPDATE_SCORE
import de.tobsinger.scoreboard.core.ui.playeradd.AddPlayerScreen
import de.tobsinger.scoreboard.core.ui.playerlist.ScoreboardScreen
import de.tobsinger.scoreboard.core.ui.updatescore.UpdateScoreScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = SCORES,
        enterTransition = { slideInLeft() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInRight() },
        popExitTransition = { slideOutRight() },
    ) {
        composable(route = SCORES) {
            ScoreboardScreen(
                addPlayer = {
                    navHostController.navigate(ADD_PLAYER)
                },
                updateScore = { playerName ->
                    navHostController.navigate("$UPDATE_SCORE/$playerName")
                }
            )
        }

        composable(route = ADD_PLAYER) {
            AddPlayerScreen(
                onNavigateBack = navHostController::popBackStack,
            )
        }

        composable(
            route = "$UPDATE_SCORE/{$PARAM_PLAYER_NAME}",
            content = { navBackStackEntry ->
                UpdateScoreScreen(
                    onNavigateBack = navHostController::popBackStack,
                    name = navBackStackEntry.arguments?.getString(PARAM_PLAYER_NAME)
                        ?: return@composable
                )
            },
            arguments = listOf(
                navArgument(PARAM_PLAYER_NAME) { type = NavType.StringType }
            )
        )
    }
}

private object Routes {
    const val SCORES = "scoreboard"
    const val ADD_PLAYER = "add-player"
    const val UPDATE_SCORE = "add-player"
}

private object Params {
    const val PARAM_PLAYER_NAME = "playerName"
}
