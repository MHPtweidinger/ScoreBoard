package de.tobsinger.scoreboard.core.ui.playeradd

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import de.tobsinger.scoreboard.core.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun NameInput(
    name: String,
    onSave: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words,
        ),
        keyboardActions = KeyboardActions { onSave() },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        onValueChange = onTextChanged,

        label = {
            Text(stringResource(R.string.player_add_input_field_label))
        }
    )
}
