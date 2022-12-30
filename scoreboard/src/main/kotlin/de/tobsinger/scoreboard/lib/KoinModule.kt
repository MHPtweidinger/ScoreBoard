package de.tobsinger.scoreboard.lib

import de.tobsinger.scoreboard.lib.service.ScoreboardService
import de.tobsinger.scoreboard.lib.service.ScoreboardServiceImpl
import de.tobsinger.scoreboard.lib.ui.playeradd.AddPlayerViewModel
import de.tobsinger.scoreboard.lib.ui.playerlist.ScoreboardViewModel
import de.tobsinger.scoreboard.lib.ui.updatescore.UpdateScoreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val scoreboardModule = module {

    viewModelOf(::ScoreboardViewModel)

    viewModelOf(::AddPlayerViewModel)

    viewModel { (name: String) ->
        UpdateScoreViewModel(
            scoreboardService = get(),
            playerName = name,
        )
    }

    singleOf(::ScoreboardServiceImpl) bind ScoreboardService::class
}
