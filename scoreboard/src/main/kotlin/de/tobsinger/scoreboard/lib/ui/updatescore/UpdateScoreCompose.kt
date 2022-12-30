package de.tobsinger.scoreboard.lib.ui.updatescore

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.lib.R
import de.tobsinger.scoreboard.lib.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun UpdateScoreCompose(name: String, onNavigateBack: () -> Unit) {

    val viewModel: UpdateScoreViewModel = getViewModel { parametersOf(name) }

    val state = viewModel.state.collectAsState()
    UpdateScoreLayout(state = state.value,
        onTextChanged = viewModel::updateInput,
        onNavigateBack = onNavigateBack,
        onAdd = {
            viewModel.add()
            onNavigateBack()
        },
        onSubtract = {
            viewModel.subtract()
            onNavigateBack()
        },
        deleteUser = {
            viewModel.deleteUser()
            onNavigateBack()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpdateScoreLayout(
    state: UpdateScoreViewModel.UpdateScoreViewState,
    onTextChanged: (String) -> Unit,
    onAdd: () -> Unit,
    deleteUser: () -> Unit,
    onSubtract: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
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
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
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

@Composable
private fun Buttons(onSubtract: () -> Unit, onAdd: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(21.dp),
        modifier = Modifier.padding(top = 21.dp),
    ) {
        Button(
            onClick = onSubtract,
            modifier = Modifier.fillMaxWidth(.5f),
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(R.string.update_score_button_subtract_label),
                modifier = Modifier.size(42.dp)
            )
        }

        Button(
            onClick = onAdd, modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.update_score_button_add_label),
                modifier = Modifier.size(42.dp)
            )

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScoreHistory(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    state: UpdateScoreViewModel.UpdateScoreViewState
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.update_score_current_score_label),
            style = Typography.titleLarge,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState,
            content = {
                items(state.scores.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = (index + 1).toString())
                        Text(text = state.scores[index].toString())
                    }
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScoreInput(
    state: UpdateScoreViewModel.UpdateScoreViewState,
    focusRequester: FocusRequester,
    onTextChanged: (String) -> Unit
) {
    TextField(value = state.userInput?.toString() ?: "",
        modifier = Modifier
            .padding(top = 21.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        onValueChange = onTextChanged,
        label = {
            Text(stringResource(R.string.update_score_input_field_label))
        })
}

@Composable
private fun CurrentScore(state: UpdateScoreViewModel.UpdateScoreViewState) {
    Row(modifier = Modifier.padding(top = 6.dp)) {
        Text(
            text = stringResource(R.string.update_score_sum_label),
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = state.scores.sum().toString(),
            modifier = Modifier.fillMaxWidth(),
            style = Typography.titleLarge,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
fun UpdateScoreComposePreview() = UpdateScoreLayout(
    state = UpdateScoreViewModel.UpdateScoreViewState(
        name = "Sandra",
        scores = listOf(2, 4, 12, -4, 6),
        userInput = 2
    ),
    onNavigateBack = {},
    onAdd = {},
    onSubtract = {},
    onTextChanged = {},
    deleteUser = {},
)
