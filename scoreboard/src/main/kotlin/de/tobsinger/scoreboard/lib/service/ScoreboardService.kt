package de.tobsinger.scoreboard.lib.service

import kotlinx.coroutines.flow.StateFlow

internal interface ScoreboardService {
    val currentScore: StateFlow<Map<String, List<Int>>>
    fun addPlayer(name: String)
    fun addPointsForPlayer(points: Int, player: String)
    fun deleteAllUsers()
    fun clearScores()
    fun deletePlayer(playerName: String)
}
