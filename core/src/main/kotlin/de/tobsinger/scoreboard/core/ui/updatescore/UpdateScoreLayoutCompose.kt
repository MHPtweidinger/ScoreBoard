package de.tobsinger.scoreboard.core.ui.updatescore

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.core.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdateScoreLayout(
    state: UpdateScoreViewModel.UpdateScoreViewState,
    onTextChanged: (String) -> Unit,
    onAdd: () -> Unit,
    deleteUser: () -> Unit,
    onSubtract: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = state.name) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }, actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "menu")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            onClick = {
                                deleteUser()
                                Toast.makeText(
                                    context,
                                    R.string.update_score_players_deleted_toast_msg,
                                    Toast.LENGTH_LONG
                                ).show()
                                showMenu = false
                            },
                            text = {
                                Text(
                                    text = stringResource(
                                        R.string.update_score_menu_delete_user_label
                                    )
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.DeleteForever,
                                    stringResource(R.string.update_score_menu_delete_user_label)
                                )
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
        ) {
            ScoreHistory(listState = listState, modifier = Modifier.weight(1f), state = state)

            CurrentScore(state)

            ScoreInput(state, focusRequester, onTextChanged)

            Buttons(onSubtract, onAdd)
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(state.scores) {
        launch {
            delay(100)
            val index = if (state.scores.isNotEmpty()) {
                state.scores.size - 1
            } else {
                0
            }
            listState.animateScrollToItem(scrollOffset = index, index = index)
        }
    }
}

@Preview
@Composable
fun UpdateScoreComposePreview() = UpdateScoreLayout(
    state = UpdateScoreViewModel.UpdateScoreViewState(
        name = "Dwight",
        scores = listOf(2, 4, 12, -4, 6),
        userInput = 2
    ),
    onNavigateBack = {},
    onAdd = {},
    onSubtract = {},
    onTextChanged = {},
    deleteUser = {},
)
