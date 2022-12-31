package de.tobsinger.scoreboard.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import de.tobsinger.scoreboard.BaseUnitTest
import de.tobsinger.scoreboard.core.service.ScoreboardService
import de.tobsinger.scoreboard.core.ui.updatescore.UpdateScoreViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateScoreViewModelTest : BaseUnitTest {
    private val mockScoreboardService: ScoreboardService = mockk(relaxed = true)

    @Test
    fun `check show current score`() = runTest {
        val playerName = "Pam"
        val pamsScores = listOf(-6, 8, 1, 2, 8, 3)
        every { mockScoreboardService.currentScore } returns flowOf(
            mapOf(
                "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                "Michal" to listOf(4, 13, 2, -2, -8, 2),
                "Andy" to listOf(7, -4, 7, 4, -4, 12),
                playerName to pamsScores,
                "Jim" to listOf(6, 4, 8, 12, 21, 7),
            )
        )
        UpdateScoreViewModel(
            scoreboardService = mockScoreboardService,
            playerName = playerName
        ).state.test {
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = pamsScores,
                    userInput = null
                )
            )
        }
    }

    @Test
    fun `check update score`() = runTest {
        val playerName = "Dwight"
        val dwightsScores = listOf(3, 18, 3, -42, 9, 2)
        every { mockScoreboardService.currentScore } returns flowOf(
            mapOf(
                "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                "Michal" to listOf(4, 13, 2, -2, -8, 2),
                "Andy" to listOf(7, -4, 7, 4, -4, 12),
                playerName to dwightsScores,
                "Jim" to listOf(6, 4, 8, 12, 21, 7),
            )
        )
        val sut = UpdateScoreViewModel(
            scoreboardService = mockScoreboardService,
            playerName = playerName
        )
        sut.state.test {
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = null
                )
            )
            sut.updateInput("5")
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = 5
                )
            )
        }
    }

    @Test
    fun `check add new score`() = runTest {
        val playerName = "Dwight"
        val dwightsScores = listOf(3, 18, 3, -42, 9, 2)
        every { mockScoreboardService.currentScore } returns flowOf(
            mapOf(
                "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                "Michal" to listOf(4, 13, 2, -2, -8, 2),
                "Andy" to listOf(7, -4, 7, 4, -4, 12),
                playerName to dwightsScores,
                "Jim" to listOf(6, 4, 8, 12, 21, 7),
            )
        )
        val sut = UpdateScoreViewModel(
            scoreboardService = mockScoreboardService,
            playerName = playerName
        )
        sut.state.test {
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = null
                )
            )
            sut.updateInput("5")
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = 5
                )
            )
            sut.addScore()
            coVerify(exactly = 1) { mockScoreboardService.addPointsForPlayer(5, playerName) }
        }
    }

    @Test
    fun `check subtract new score`() = runTest {
        val playerName = "Dwight"
        val dwightsScores = listOf(3, 18, 3, -42, 9, 2)
        every { mockScoreboardService.currentScore } returns flowOf(
            mapOf(
                "Dwight" to listOf(2, -4, 12, 7, -4, 12),
                "Michal" to listOf(4, 13, 2, -2, -8, 2),
                "Andy" to listOf(7, -4, 7, 4, -4, 12),
                playerName to dwightsScores,
                "Jim" to listOf(6, 4, 8, 12, 21, 7),
            )
        )
        val sut = UpdateScoreViewModel(
            scoreboardService = mockScoreboardService,
            playerName = playerName
        )
        sut.state.test {
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = null
                )
            )
            sut.updateInput("5")
            assertThat(awaitItem()).isEqualTo(
                UpdateScoreViewModel.UpdateScoreViewState(
                    name = playerName,
                    scores = dwightsScores,
                    userInput = 5
                )
            )
            sut.subtractScore()
            coVerify(exactly = 1) { mockScoreboardService.addPointsForPlayer(-5, playerName) }
        }
    }
}
