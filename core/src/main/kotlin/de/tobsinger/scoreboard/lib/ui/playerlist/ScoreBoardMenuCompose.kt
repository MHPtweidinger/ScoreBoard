package de.tobsinger.scoreboard.lib.ui.playerlist

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.tobsinger.scoreboard.lib.R


@Composable
internal fun ScreboardMenu(
    showMenu: Boolean,
    clearScores: () -> Unit,
    hideMenu: () -> Unit,
    deleteAllPlayers: () -> Unit
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = hideMenu
    ) {
        DropdownMenuItem(
            onClick = {
                clearScores()
                Toast.makeText(
                    context,
                    R.string.score_board_scores_cleared_toast_msg,
                    Toast.LENGTH_LONG
                ).show()
                hideMenu()
            },
            text = { Text(stringResource(R.string.score_board_menu_item_clear)) },
            leadingIcon = {
                Icon(
                    Icons.Filled.ClearAll,
                    stringResource(R.string.score_board_menu_item_clear)
                )
            })
        DropdownMenuItem(
            onClick = {
                deleteAllPlayers()
                Toast.makeText(
                    context,
                    R.string.score_board_players_cleared_toast_msg,
                    Toast.LENGTH_LONG
                ).show()
                hideMenu()
            },
            text = { Text(stringResource(R.string.score_board_menu_item_delete)) },
            leadingIcon = {
                Icon(
                    Icons.Filled.DeleteForever,
                    stringResource(R.string.score_board_menu_item_delete)
                )
            }
        )
    }
}
