package de.tobsinger.scoreboard.lib.ui.playeradd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.getViewModel

@Composable
internal fun AddPlayerScreen(onNavigateBack: () -> Unit) {

    val viewModel: AddPlayerViewModel = getViewModel()
    val state = viewModel.state.collectAsState()
    AddPlayerLayout(
        name = state.value,
        onTextChanged = viewModel::onTextChanged,
        onNavigateBack = onNavigateBack,
        onSave = {
            viewModel.onSave()
            onNavigateBack()
        }
    )
}


