package net.wandroid.mytodo.features.auth.presentation.login_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
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
import net.wandroid.mytodo.features.auth.presentation.login_screen.LoginState

@Composable
internal fun LoginComponent(
    state: LoginState,
    modifier: Modifier,
    onRegister: () -> Unit,
    onLogin: (userName: String, password: String) -> Unit,
    onFieldsUpdated: (userName: String, password: String) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.userName,
                onValueChange = { text ->
                    onFieldsUpdated(
                        text,
                        state.password,
                    )
                },
                label = { Text(stringResource(R.string.login_user_name)) },
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.password,
                onValueChange = { text ->
                    onFieldsUpdated(
                        state.userName,
                        text,
                    )
                },
                label = { Text(stringResource(R.string.login_password)) },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = !state.isLoading,
                onClick = {
                    onLogin(
                        state.userName,
                        state.password
                    )
                }
            ) {
                Text(stringResource(R.string.login_button))
            }
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = !state.isLoading,
                onClick = onRegister,
            ) {
                Text(stringResource(R.string.login_register_button))
            }
            if (state.error != null) {
                Text("${state.error}")
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
fun PreviewLoginComponent(
) {
    val state = LoginState.Init
    LoginComponent(
        state = state,
        modifier = Modifier,
        onLogin = { _, _ -> },
        onRegister = {},
        onFieldsUpdated = { _, _ -> }
    )
}