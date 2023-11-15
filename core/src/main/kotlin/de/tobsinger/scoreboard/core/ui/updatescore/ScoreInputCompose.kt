package de.tobsinger.scoreboard.core.ui.updatescore

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.core.R

@Composable
internal fun ScoreInput(
    state: UpdateScoreViewModel.UpdateScoreViewState,
    focusRequester: FocusRequester,
    onTextChanged: (String) -> Unit
) {
    TextField(value = state.userInput?.toString() ?: "",
        modifier = Modifier
            .padding(top = 21.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        onValueChange = onTextChanged,
        label = {
            Text(stringResource(R.string.update_score_input_field_label))
        })
}
