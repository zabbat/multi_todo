package net.wandroid.mytodo.features.auth.presentation.create_user_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.wandroid.mytodo.features.auth.R
import net.wandroid.mytodo.features.auth.presentation.create_user_screen.CreateUserState

@Composable
internal fun CreateUserComponent(
    state: CreateUserState,
    onFieldUpdated: (
        userName: String,
        password: String,
        rePassword: String,
    ) -> Unit,
    onRegister: (
        userName: String,
        password: String,
        rePassword: String,
    ) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.userName,
                onValueChange = { text ->
                    onFieldUpdated(text, state.password, state.rePassword)
                },
                label = { Text(stringResource(R.string.create_user_email)) },
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.password,
                onValueChange = { text ->
                    onFieldUpdated(state.userName, text, state.rePassword)
                },
                label = { Text(stringResource(R.string.create_user_password)) },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.rePassword,
                onValueChange = { text ->
                    onFieldUpdated(state.userName, state.password, text)
                },
                label = { Text(stringResource(R.string.create_user_re_entry_password)) },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = !state.isLoading && !state.registrationCompleted,
                onClick = {
                    onRegister(state.userName, state.password, state.rePassword)
                }
            ) {
                Text(stringResource(R.string.register_button))
            }
            if (state.error != null) {
                Text(
                    stringResource(R.string.register_error, state.error)
                )
            }
            if (state.registrationCompleted) {
                Text(stringResource(R.string.register_completed))
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}

@Composable
@Preview
fun PreviewCreateUserComponent(
) {
    val state = CreateUserState.Init
    CreateUserComponent(
        state = state,
        onRegister = { _, _, _ -> },
        onFieldUpdated = { _, _, _ -> },
        modifier = Modifier,
    )
}