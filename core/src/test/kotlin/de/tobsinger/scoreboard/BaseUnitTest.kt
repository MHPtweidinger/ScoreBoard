package de.tobsinger.scoreboard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

interface BaseUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun prepare() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }
}
