package de.tobsinger.scoreboard.lib.ui.playeradd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.lib.R
import de.tobsinger.scoreboard.lib.ui.theme.Typography
import org.koin.androidx.compose.getViewModel

@Composable
internal fun AddPlayerCompose(onNavigateBack: () -> Unit) {

    val viewModel: AddPlayerViewModel = getViewModel()
    val state = viewModel.state.collectAsState()
    AddPlayerLayout(
        name = state.value,
        onTextChanged = viewModel::onTextChanged,
        onNavigateBack = onNavigateBack,
        onSave = {
            viewModel.onSave()
            onNavigateBack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPlayerLayout(
    name: String,
    onTextChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(R.string.player_add_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                value = name,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Words,
                ),
                keyboardActions = KeyboardActions { onSave() },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onValueChange = onTextChanged,

                label = {
                    Text(stringResource(R.string.player_add_input_field_label))
                }
            )

            Button(
                onClick = onSave, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.player_add_button_save_label),
                    style = Typography.titleMedium,
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun AddPlayerComposePreview() =
    AddPlayerLayout(
        onNavigateBack = {},
        name = "Sandra",
        onTextChanged = {},
        onSave = {}
    )

