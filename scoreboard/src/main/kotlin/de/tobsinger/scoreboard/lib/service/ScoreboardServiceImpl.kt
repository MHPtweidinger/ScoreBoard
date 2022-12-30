package de.tobsinger.scoreboard.lib.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ScoreboardServiceImpl : ScoreboardService {

    private val _currentScore: MutableStateFlow<Map<String, List<Int>>> =
        MutableStateFlow(emptyMap())

    override val currentScore: StateFlow<Map<String, List<Int>>> = _currentScore.asStateFlow()

    override fun addPlayer(name: String) {
        val tmp = _currentScore.value.toMutableMap()
        tmp[name] = emptyList()
        _currentScore.value = tmp
    }

    override fun addPointsForPlayer(points: Int, player: String) {
        val tmp = _currentScore.value.toMutableMap()
        val scores = tmp[player]?.toMutableList() ?: mutableListOf()
        scores.add(points)
        tmp[player] = scores
        _currentScore.value = tmp
    }

    override fun deleteAllUsers() {
        _currentScore.value = emptyMap()
    }

    override fun clearScores() {
        val newList = mutableMapOf<String, List<Int>>()
        _currentScore.value.keys.forEach { key -> newList[key] = emptyList() }
        _currentScore.value = newList
    }

    override fun deletePlayer(playerName: String) {
        val tmp = _currentScore.value.toMutableMap()
        tmp.remove(playerName)
        _currentScore.value = tmp
    }
}
