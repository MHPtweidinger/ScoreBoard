package de.tobsinger.scoreboard.core.ui.playeradd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.tobsinger.scoreboard.core.R
import de.tobsinger.scoreboard.core.ui.theme.Typography
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddPlayerLayout(
    name: String,
    onTextChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
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
                .padding(horizontal = 20.dp)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            NameInput(
                name = name,
                onSave = onSave,
                focusRequester = focusRequester,
                modifier = Modifier.testTag("name_input"),
                onTextChanged = onTextChanged
            )

            Button(
                onClick = onSave, modifier = Modifier
                    .testTag("submit")
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
        delay(100)
        focusRequester.requestFocus()
    }
}


@Preview
@Composable
fun AddPlayerComposePreview() =
    AddPlayerLayout(
        onNavigateBack = {},
        name = "Pam",
        onTextChanged = {},
        onSave = {}
    )
