package de.tobsinger.scoreboard.service

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import de.tobsinger.scoreboard.core.service.ScoreboardServiceImpl
import de.tobsinger.scoreboard.persistence.ScoreboardPersistence
import de.tobsinger.scoreboard.persistence.ScoreboardState
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScoreboardServiceImplTest {

    private val mockScoreboardPersistence: ScoreboardPersistence = mockk(relaxed = true)

    @Test
    fun `check empty initial state`() = runTest {
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(emptyMap()))
        ScoreboardServiceImpl(mockScoreboardPersistence).currentScore.test {
            assertThat(awaitItem()).isEqualTo(emptyMap<String, List<Int>>())
            assertThat(awaitComplete()).isEqualTo(Unit)
        }
    }

    @Test
    fun `check initial state`() = runTest {
        val players = mapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).currentScore.test {
            assertThat(awaitItem()).isEqualTo(players)
            assertThat(awaitComplete()).isEqualTo(Unit)
        }
    }

    @Test
    fun `check add player`() = runTest {
        val newPlayer = "Mose"
        val players = mutableMapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).addPlayer(newPlayer)
        players[newPlayer] = emptyList()
        coVerify(exactly = 1) { mockScoreboardPersistence.persistState(ScoreboardState(players)) }
    }

    @Test
    fun `check add empty player`() = runTest {
        val newPlayer = " "
        val players = mutableMapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).addPlayer(newPlayer)
        coVerify(exactly = 0) { mockScoreboardPersistence.persistState(any()) }
    }

    @Test
    fun `check add points for player`() = runTest {
        val playerName = "Andy"
        val newScore = 4
        val players = mapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            playerName to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).addPointsForPlayer(newScore, playerName)
        coVerify(exactly = 1) {
            mockScoreboardPersistence.persistState(
                ScoreboardState(
                    mapOf(
                        "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                        "Michael" to listOf(4, 13, 2, -2, -8, 2),
                        playerName to listOf(7, -4, 7, 4, -4, 12, newScore),
                        "Jim" to listOf(6, 4, 8, 12, 21, 7),
                    )
                )
            )
        }
    }

    @Test
    fun `check delete all players`() = runTest {
        val players = mapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).deleteAllPlayers()
        coVerify(exactly = 1) {
            mockScoreboardPersistence.persistState(
                ScoreboardState(
                   emptyMap()
                )
            )
        }
    }

    @Test
    fun `check clear all scores`() = runTest {
        val players = mapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            "Michael" to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).clearScores()
        coVerify(exactly = 1) {
            mockScoreboardPersistence.persistState(
                ScoreboardState(
                    mapOf(
                        "Dwight" to emptyList(),
                        "Michael" to emptyList(),
                        "Andy" to emptyList(),
                        "Jim" to emptyList(),
                    )
                )
            )
        }
    }

    @Test
    fun `check delete single player`() = runTest {
        val playerName = "Michael"
        val players = mapOf(
            "Dwight" to listOf(2, -4, 12, 7, -4, 12),
            playerName to listOf(4, 13, 2, -2, -8, 2),
            "Andy" to listOf(7, -4, 7, 4, -4, 12),
            "Jim" to listOf(6, 4, 8, 12, 21, 7),
        )
        every { mockScoreboardPersistence.data } returns flowOf(ScoreboardState(players))
        ScoreboardServiceImpl(mockScoreboardPersistence).deletePlayer(playerName)
        coVerify(exactly = 1) {
            mockScoreboardPersistence.persistState(
                ScoreboardState(
                    mapOf(
                        "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                        "Andy" to listOf(7, -4, 7, 4, -4, 12),
                        "Jim" to listOf(6, 4, 8, 12, 21, 7),
                    )
                )
            )
        }
    }
}
