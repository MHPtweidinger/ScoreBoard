package de.tobsinger.scoreboard.lib.ui.updatescore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun UpdateScoreScreen(name: String, onNavigateBack: () -> Unit) {

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
