package de.tobsinger.scoreboard.core.ui.playerlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.core.R

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
internal fun ScoreboardLayout(
    state: ScoreboardState,
    addPlayer: () -> Unit,
    updateScore: (String) -> Unit,
    clearScores: () -> Unit,
    fabInitiallyVisible: Boolean = false,
    deleteAllPlayers: () -> Unit,
) {
    val fabVisibilityState = remember(state) {
        MutableTransitionState(fabInitiallyVisible).apply {
            targetState = true
        }
    }
    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.score_board_title)) },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "menu")
                    }
                    ScreboardMenu(
                        showMenu = showMenu,
                        clearScores = clearScores,
                        deleteAllPlayers = deleteAllPlayers,
                        hideMenu = { showMenu = false }
                    )
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visibleState = fabVisibilityState,
                enter = scaleIn(),
                exit = scaleOut()
            )
            {
                FloatingActionButton(
                    onClick = addPlayer,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .testTag("addPlayer"),
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        }

    ) { paddingValues ->
        PlayersWithScores(paddingValues, state, updateScore)
    }
}

@Preview
@Composable
fun ScoreboardLayoutPreview() {
    ScoreboardLayout(
        state = ScoreboardState(
            score = listOf(
                PlayerStats("Michael", -82),
                PlayerStats("Dwight", 36),
                PlayerStats("Andy", 36),
                PlayerStats("Jim", 13),
                PlayerStats("Pam", 42),
            )
        ),
        deleteAllPlayers = {},
        clearScores = {},
        addPlayer = {},
        updateScore = {},
        fabInitiallyVisible = true,
    )
}

