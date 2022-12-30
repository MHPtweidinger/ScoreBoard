package de.tobsinger.scoreboard.ui

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import de.tobsinger.scoreboard.lib.service.ScoreboardService
import de.tobsinger.scoreboard.lib.ui.playeradd.AddPlayerViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddPlayerViewModelTest : BaseUnitTest() {

    private val mockScoreboardService: ScoreboardService = mockk(relaxed = true)
    private val sut = AddPlayerViewModel(mockScoreboardService)

    @Test
    fun `check edit name`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isEqualTo("")
            sut.onTextChanged("Fri")
            assertThat(awaitItem()).isEqualTo("Fri")
            sut.onTextChanged("Fritz")
            assertThat(awaitItem()).isEqualTo("Fritz")
        }
    }

    @Test
    fun `check submit name`() = runTest {
        sut.state.test {
            assertThat(awaitItem()).isEqualTo("")
            sut.onTextChanged("Paul")
            assertThat(awaitItem()).isEqualTo("Paul")
            sut.onSave()
            coVerify(exactly = 1) { mockScoreboardService.addPlayer("Paul") }
        }
    }
}
