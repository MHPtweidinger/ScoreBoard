package de.tobsinger.scoreboard.core.ui.playeradd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tobsinger.scoreboard.core.service.ScoreboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class AddPlayerViewModel(private val scoreboardService: ScoreboardService) : ViewModel() {

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    fun onTextChanged(text: String) {
        _state.value = text
    }

    fun onSave() {
        viewModelScope.launch {
            scoreboardService.addPlayer(state.value)
        }
    }
}
