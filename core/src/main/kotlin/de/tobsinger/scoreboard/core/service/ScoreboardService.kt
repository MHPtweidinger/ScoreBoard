package de.tobsinger.scoreboard.core.service

import kotlinx.coroutines.flow.Flow

internal interface ScoreboardService {

    /**
     * The collection of players with each history of scored points
     */
    val currentScore: Flow<Map<String, List<Int>>>

    /**
     * Add a new player to the board
     */
    suspend fun addPlayer(name: String)

    /**
     * Add a new entry of scored points for a given user
     */
    suspend fun addPointsForPlayer(points: Int, player: String)

    /**
     * Clear the database for all players and scores
     */
    suspend fun deleteAllPlayers()

    /**
     * Remove all scores for all players
     */
    suspend fun clearScores()

    /**
     * Delete a player with the given name from the score board
     */
    suspend fun deletePlayer(playerName: String)
}
