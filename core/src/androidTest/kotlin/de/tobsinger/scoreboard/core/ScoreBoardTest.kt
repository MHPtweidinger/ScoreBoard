package de.tobsinger.scoreboard.core

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.tobsinger.scoreboard.core.ui.ScoreboardUi
import de.tobsinger.scoreboard.core.ui.theme.ScoreboardTheme
import de.tobsinger.scoreboard.persistence.ScoreboardPersistence
import de.tobsinger.scoreboard.persistence.ScoreboardState
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScoreBoardTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val scoreBoardPersistence: ScoreboardPersistence = mockk(relaxed = true)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single { scoreBoardPersistence }
            },
            scoreboardModule,
        )
    }

    @Test
    fun check_add_player() {
        with(composeTestRule) {
            setContent {
                ScoreboardTheme(darkTheme = false, dynamicColor = true) {
                    ScoreboardUi()
                }
            }
            onNodeWithTag("addPlayer").assertIsDisplayed()
            onNodeWithTag("addPlayer").performClick()
            onNodeWithTag("name_input").performTextInput("Michael")
            every { scoreBoardPersistence.data }.returns(
                flowOf(ScoreboardState(emptyMap()))
            )
            onNodeWithTag("submit").performClick()
            coVerify(exactly = 1) {
                scoreBoardPersistence.persistState(ScoreboardState(mapOf("Michael" to emptyList())))
            }
            onNodeWithTag("addPlayer").assertIsDisplayed()
        }
    }

    @Test
    fun check_multiple_players() {
        every { scoreBoardPersistence.data }.returns(
            flowOf( ScoreboardState(mapOf("Michael" to emptyList())))
        )
        with(composeTestRule) {
            setContent {
                ScoreboardTheme(darkTheme = false, dynamicColor = true) {
                    ScoreboardUi()
                }
            }

            onNodeWithTag("addPlayer").assertIsDisplayed()
            onNodeWithText("Michael").assertIsDisplayed()
        }
    }
}
