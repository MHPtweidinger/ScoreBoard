package de.tobsinger.scoreboard.persistence

import kotlinx.coroutines.flow.Flow

interface ScoreboardPersistence {

    val data: Flow<ScoreboardState>

    suspend fun persistState(newState: ScoreboardState)
}
