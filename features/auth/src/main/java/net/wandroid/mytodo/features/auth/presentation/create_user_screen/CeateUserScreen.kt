package net.wandroid.mytodo.features.auth.presentation.create_user_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import net.wandroid.mytodo.features.auth.presentation.create_user_screen.components.CreateUserComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateUserScreen(
    onRegistrationCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: CreateUserViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.effect.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle,
            minActiveState = Lifecycle.State.STARTED,
        ).collect { event ->
            when (event) {
                CreateUserEffect.RegistrationCompletedEffect -> onRegistrationCompleted()
            }
        }
    }

    CreateUserComponent(
        state = state,
        modifier = modifier,
        onRegister = { userName, password, rePassword ->
            viewModel.dispatch(
                intent = CreateUserIntent.RegisterUserIntent(
                    userName,
                    password,
                    rePassword,
                )
            )
        },
        onFieldUpdated = { userName, password, rePassword ->
            viewModel.dispatch(
                intent = CreateUserIntent.UpdateRegistrationUserIntent(
                    userName,
                    password,
                    rePassword
                )
            )
        }
    )
}