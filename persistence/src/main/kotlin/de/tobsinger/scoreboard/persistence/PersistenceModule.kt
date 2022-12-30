package de.tobsinger.scoreboard.persistence

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val persistenceModule = module {
    single<ScoreboardPersistence> { ScoreboardPersistenceImpl(androidContext())}
}
