package de.tobsinger.scoreboard.persistence

import kotlinx.serialization.Serializable

@Serializable
data class ScoreboardState(val value: Map<String, List<Int>>)
