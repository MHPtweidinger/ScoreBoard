package de.tobsinger.scoreboard.lib.ui.updatescore

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.lib.R
import de.tobsinger.scoreboard.lib.ui.theme.Typography

@Composable
internal fun CurrentScore(state: UpdateScoreViewModel.UpdateScoreViewState) {
    Row(modifier = Modifier.padding(top = 6.dp)) {
        Text(
            text = stringResource(R.string.update_score_sum_label),
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = state.scores.sum().toString(),
            modifier = Modifier.fillMaxWidth(),
            style = Typography.titleLarge,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
