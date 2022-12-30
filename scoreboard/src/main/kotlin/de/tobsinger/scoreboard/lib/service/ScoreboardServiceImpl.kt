package de.tobsinger.scoreboard.lib.service

import de.tobsinger.scoreboard.persistence.ScoreboardPersistence
import de.tobsinger.scoreboard.persistence.ScoreboardState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class ScoreboardServiceImpl(private val scoreboardPersistence: ScoreboardPersistence) :
    ScoreboardService {

    override val currentScore: Flow<Map<String, List<Int>>> =
        scoreboardPersistence.data.map { it.value }

    override suspend fun addPlayer(name: String) {
        val tmp = scoreboardPersistence.data.first().value.toMutableMap()
        tmp[name] = emptyList()
        scoreboardPersistence.persistState(ScoreboardState(tmp))
    }

    override suspend fun addPointsForPlayer(points: Int, player: String) {
        val tmp = scoreboardPersistence.data.first().value.toMutableMap()
        val scores = tmp[player]?.toMutableList() ?: mutableListOf()
        scores.add(points)
        tmp[player] = scores
        scoreboardPersistence.persistState(ScoreboardState(tmp))
    }

    override suspend fun deleteAllUsers() {
        scoreboardPersistence.persistState(ScoreboardState(emptyMap()))
    }

    override suspend fun clearScores() {
        val tmp = mutableMapOf<String, List<Int>>()
        scoreboardPersistence.data.first().value.keys.forEach { key -> tmp[key] = emptyList() }
        scoreboardPersistence.persistState(ScoreboardState(tmp))
    }

    override suspend fun deletePlayer(playerName: String) {
        val tmp =  scoreboardPersistence.data.first().value.toMutableMap()
        tmp.remove(playerName)
        scoreboardPersistence.persistState(ScoreboardState(tmp))
    }
}
