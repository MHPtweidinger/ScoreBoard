package de.tobsinger.scoreboard.lib.ui.playerlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.getViewModel

@Composable
internal fun ScoreboardScreen(
    addPlayer: () -> Unit,
    updateScore: (String) -> Unit,
) {
    val viewModel: ScoreboardViewModel = getViewModel()
    val state = viewModel.state.collectAsState()

    ScoreboardLayout(
        state = state.value,
        addPlayer = addPlayer,
        updateScore = updateScore,
        clearScores = viewModel::clearScores,
        deleteAllPlayers = viewModel::deleteAllUsers
    )
}
