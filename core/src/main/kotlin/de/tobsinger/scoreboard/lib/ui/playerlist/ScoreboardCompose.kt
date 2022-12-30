package de.tobsinger.scoreboard.lib.ui.playerlist

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.lib.R
import de.tobsinger.scoreboard.lib.ui.theme.Typography
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

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
private fun ScoreboardLayout(
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
                        .padding(all = 16.dp),
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        }

    ) { paddingValues ->
        PlayersWithScores(paddingValues, state, updateScore)
    }
}

@Composable
private fun ScreboardMenu(
    showMenu: Boolean,
    clearScores: () -> Unit,
    hideMenu: () -> Unit,
    deleteAllPlayers: () -> Unit
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = hideMenu
    ) {
        DropdownMenuItem(
            onClick = {
                clearScores()
                Toast.makeText(
                    context,
                    R.string.score_board_scores_cleared_toast_msg,
                    Toast.LENGTH_LONG
                ).show()
                hideMenu()
            },
            text = { Text(stringResource(R.string.score_board_menu_item_clear)) },
            leadingIcon = {
                Icon(
                    Icons.Filled.ClearAll,
                    stringResource(R.string.score_board_menu_item_clear)
                )
            })
        DropdownMenuItem(
            onClick = {
                deleteAllPlayers()
                Toast.makeText(
                    context,
                    R.string.score_board_players_cleared_toast_msg,
                    Toast.LENGTH_LONG
                ).show()
                hideMenu()
            },
            text = { Text(stringResource(R.string.score_board_menu_item_delete)) },
            leadingIcon = {
                Icon(
                    Icons.Filled.DeleteForever,
                    stringResource(R.string.score_board_menu_item_delete)
                )
            }
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PlayersWithScores(
    paddingValues: PaddingValues,
    state: ScoreboardState,
    updateScore: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(20.dp),
        ) {
            items(state.score.size) { index ->
                Card(modifier = Modifier.animateItemPlacement()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { updateScore(state.score[index].playerName) }
                            .padding(12.dp),
                    ) {
                        Text(
                            text = state.score[index].playerName,
                            style = Typography.titleLarge,
                        )
                        Text(text = state.score[index].score.toString())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ScoreboardLayoutPreview() {
    ScoreboardLayout(
        state = ScoreboardState(
            score = listOf(
                PlayerStats("Sandra", 42),
                PlayerStats("Tobias", 36),
            )
        ),
        deleteAllPlayers = {},
        clearScores = {},
        addPlayer = {},
        updateScore = {},
        fabInitiallyVisible = true,
    )
}
