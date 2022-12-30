package de.tobsinger.scoreboard.core.service

import kotlinx.coroutines.flow.Flow

internal interface ScoreboardService {
    val currentScore: Flow<Map<String, List<Int>>>
    suspend fun addPlayer(name: String)
    suspend fun addPointsForPlayer(points: Int, player: String)
    suspend fun deleteAllUsers()
    suspend fun clearScores()
    suspend fun deletePlayer(playerName: String)
}
