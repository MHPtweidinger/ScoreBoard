package de.tobsinger.scoreboard.core.ui.updatescore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tobsinger.scoreboard.core.service.ScoreboardService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class UpdateScoreViewModel(
    private val scoreboardService: ScoreboardService,
    private val playerName: String
) :
    ViewModel() {

    private val _inputState: MutableStateFlow<Int?> = MutableStateFlow(null)
    val state: StateFlow<UpdateScoreViewState> =
        combine(scoreboardService.currentScore, _inputState) { currentScore, input ->
            UpdateScoreViewState(
                name = playerName,
                scores = currentScore[playerName] ?: emptyList(),
                userInput = input,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UpdateScoreViewState(
                name = playerName,
                scores = emptyList(),
                userInput = 0,
            )
        )

    fun updateInput(input: String) {
        _inputState.value = input.toIntOrNull()
    }

    fun addScore() {
        _inputState.value?.let { points ->
            viewModelScope.launch {
                scoreboardService.addPointsForPlayer(
                    points = points,
                    player = playerName
                )
            }
        }
    }

    fun subtractScore() {
        _inputState.value?.let { points ->
            viewModelScope.launch {
                scoreboardService.addPointsForPlayer(
                    points = points * -1,
                    player = playerName
                )
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            scoreboardService.deletePlayer(playerName)
        }
    }

    data class UpdateScoreViewState(val name: String, val scores: List<Int>, val userInput: Int?)
}

