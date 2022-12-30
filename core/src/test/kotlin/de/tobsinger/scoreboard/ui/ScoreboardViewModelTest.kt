package de.tobsinger.scoreboard.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import de.tobsinger.scoreboard.core.service.ScoreboardService
import de.tobsinger.scoreboard.core.ui.playerlist.PlayerStats
import de.tobsinger.scoreboard.core.ui.playerlist.ScoreboardState
import de.tobsinger.scoreboard.core.ui.playerlist.ScoreboardViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScoreboardViewModelTest : BaseUnitTest {
    private val mockScoreboardService: ScoreboardService = mockk(relaxed = true)

    @Test
    fun `check initial state`() = runTest {
        val playerA = "Tobs"
        val playerB = "Sanni"
        every { mockScoreboardService.currentScore } returns flowOf(
            mapOf(
                playerA to listOf(1, 2, -5, 2, -8, 0),
                playerB to listOf(3, 8, 12, 6, -2, 13)
            )
        )
        val sut = ScoreboardViewModel(mockScoreboardService)

        sut.state.test {
            assertThat(awaitItem()).isEqualTo(
                ScoreboardState(
                    listOf(
                        PlayerStats(playerA, -8),
                        PlayerStats(playerB, 40)
                    )
                )
            )
        }
    }

    @Test
    fun `check empty state`() = runTest {
        every { mockScoreboardService.currentScore } returns emptyFlow()
        val sut = ScoreboardViewModel(mockScoreboardService)

        sut.state.test {
            assertThat(awaitItem()).isEqualTo(
                ScoreboardState(
                    emptyList()
                )
            )
        }
    }

    @Test
    fun `check delete players`() = runTest {
        ScoreboardViewModel(mockScoreboardService).deleteAllUsers()
        coVerify(exactly = 1) { mockScoreboardService.deleteAllPlayers() }
    }

    @Test
    fun `check clear scores`() = runTest {
        ScoreboardViewModel(mockScoreboardService).clearScores()
        coVerify(exactly = 1) { mockScoreboardService.clearScores() }
    }
}
