package de.tobsinger.scoreboard.lib.ui.playerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.tobsinger.scoreboard.lib.service.ScoreboardService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ScoreboardViewModel(private val scoreboardService: ScoreboardService) : ViewModel() {

    val state = scoreboardService.currentScore.map { currentScore ->
        ScoreboardState(
            score = currentScore.toList().map {
                PlayerStats(playerName = it.first, score = it.second.sum())
            })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ScoreboardState(emptyList())
    )

    fun clearScores() {
        scoreboardService.clearScores()
    }

    fun deleteAllUsers() {
        scoreboardService.deleteAllUsers()
    }
}

internal data class ScoreboardState(val score: List<PlayerStats>)

internal data class PlayerStats(val playerName: String, val score: Int)
