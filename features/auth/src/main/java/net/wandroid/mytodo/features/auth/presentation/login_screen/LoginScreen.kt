package net.wandroid.mytodo.features.auth.presentation.login_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import net.wandroid.mytodo.features.auth.presentation.login_screen.components.LoginComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onRegisterEvent: () -> Unit,
    onLoggedInEvent: () -> Unit,
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.effect.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = Lifecycle.State.STARTED
        ).collect { event ->
            when (event) {
                LoginEffect.RegisterEffect -> onRegisterEvent()
                LoginEffect.LoggedInEffect -> onLoggedInEvent()
            }
        }
    }
    LoginComponent(
        state = state,
        modifier = modifier,
        onRegister = {
            viewModel.dispatch(intent = LoginIntent.RegisterIntent)
        },
        onLogin = { userName, password ->
            viewModel.dispatch(
                intent = LoginIntent.LoginUserIntent(
                    userName,
                    password
                )
            )
        },
        onFieldsUpdated = { userName, password ->
            viewModel.dispatch(
                intent = LoginIntent.UpdateLoginIntent(
                    userName,
                    password
                )
            )
        }
    )
}