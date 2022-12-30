package de.tobsinger.scoreboard

import android.app.Application
import de.tobsinger.scoreboard.core.scoreboardModule
import de.tobsinger.scoreboard.persistence.persistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ScoreboardApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@ScoreboardApp)

            // Load modules
            modules(scoreboardModule, persistenceModule)
        }
    }
}
