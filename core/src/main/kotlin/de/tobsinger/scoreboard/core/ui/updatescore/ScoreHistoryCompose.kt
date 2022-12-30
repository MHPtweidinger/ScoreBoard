package de.tobsinger.scoreboard.lib.ui.updatescore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.tobsinger.scoreboard.lib.R
import de.tobsinger.scoreboard.lib.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ScoreHistory(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    state: UpdateScoreViewModel.UpdateScoreViewState
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.update_score_current_score_label),
            style = Typography.titleLarge,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState,
            content = {
                items(state.scores.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = (index + 1).toString())
                        Text(text = state.scores[index].toString())
                    }
                }
            }
        )
    }
}
