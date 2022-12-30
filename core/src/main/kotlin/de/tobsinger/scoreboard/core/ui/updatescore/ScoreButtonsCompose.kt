package de.tobsinger.scoreboard.core.ui.updatescore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.core.R

@Composable
internal fun Buttons(onSubtract: () -> Unit, onAdd: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(21.dp),
        modifier = Modifier.padding(top = 21.dp),
    ) {
        Button(
            onClick = onSubtract,
            modifier = Modifier.fillMaxWidth(.5f),
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(R.string.update_score_button_subtract_label),
                modifier = Modifier.size(42.dp)
            )
        }

        Button(
            onClick = onAdd, modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.update_score_button_add_label),
                modifier = Modifier.size(42.dp)
            )

        }
    }
}
