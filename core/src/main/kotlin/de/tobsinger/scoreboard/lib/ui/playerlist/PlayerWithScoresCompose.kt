package de.tobsinger.scoreboard.lib.ui.playerlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.lib.ui.theme.Typography

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun PlayersWithScores(
    paddingValues: PaddingValues,
    state: ScoreboardState,
    updateScore: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(20.dp),
        ) {
            items(state.score.size) { index ->
                Card(modifier = Modifier.animateItemPlacement()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { updateScore(state.score[index].playerName) }
                            .padding(12.dp),
                    ) {
                        Text(
                            text = state.score[index].playerName,
                            style = Typography.titleLarge,
                        )
                        Text(text = state.score[index].score.toString())
                    }
                }
            }
        }
    }
}
