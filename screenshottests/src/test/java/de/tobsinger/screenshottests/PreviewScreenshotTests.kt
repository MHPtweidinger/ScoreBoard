/*
 * Dr. Ing. h.c. F. Porsche AG confidential. This code is protected by intellectual property rights.
 * The Dr. Ing. h.c. F. Porsche AG owns exclusive legal rights of use.
 */
package de.tobsinger.screenshottests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
//import androidx.compose.material3.dynamicDarkColorScheme
//import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import de.tobsinger.scoreboard.lib.getMetadata
import de.tobsinger.scoreboard.lib.ui.theme.ScoreboardTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(TestParameterInjector::class)
class PreviewScreenshotTests {

    class PreviewParameter(private val showkasePreview: ShowkaseBrowserComponent) {
        val content: @Composable () -> Unit = showkasePreview.component
        override fun toString(): String =
            "${showkasePreview.group} - ${showkasePreview.componentName}"
    }

    object PreviewProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues(): List<PreviewParameter> =
            Showkase.getMetadata().componentList.map(::PreviewParameter)
    }

    enum class ScreenshotTestDevice(val deviceConfig: DeviceConfig) {
        PIXEL_5(DeviceConfig.PIXEL_5),
        PIXEL_5_DARK(DeviceConfig.PIXEL_5.copy(nightMode = NightMode.NIGHT)),
        PIXEL_5_LARGE_FONTS(DeviceConfig.PIXEL_5.copy(fontScale = 1.5f)),
        PIXEL_6_PRO_DE(DeviceConfig.PIXEL_6_PRO.copy(locale = Locale.GERMAN.toString()))
    }

    @get:Rule
    val paparazzi = Paparazzi(
        maxPercentDifference = 0.01,
    )

    @Before
    fun setup() {
        setDefaultLocaleForScreenshots()
    }

    @Test
    fun screenshotTests(
        @TestParameter(valuesProvider = PreviewProvider::class) showkasePreview: PreviewParameter,
        @TestParameter testDevice: ScreenshotTestDevice
    ) {
        paparazzi.unsafeUpdateConfig(
            testDevice.deviceConfig.copy(softButtons = false, )
        )
        paparazzi.snapshot {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                val isDarkTheme = testDevice.deviceConfig.nightMode == NightMode.NIGHT
                ScoreboardTheme(darkTheme = isDarkTheme) {
//                    val colorScheme = if (isDarkTheme) {
//                        dynamicDarkColorScheme(LocalContext.current)
//                    } else {
//                        dynamicLightColorScheme(LocalContext.current)
//                    }
                    Box(
//                        Modifier.background(colorScheme.background)
                    ) {
                        showkasePreview.content()
                    }
                }
            }
        }
    }

    private fun setDefaultLocaleForScreenshots() {
//        Locale.setDefault(Locale.ENGLISH)
    }

    private companion object {
        private const val DEVICE_DEFAULT_LOCALE = "en"
    }
}
