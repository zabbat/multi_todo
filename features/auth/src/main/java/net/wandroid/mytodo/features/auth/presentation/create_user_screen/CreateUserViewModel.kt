package net.wandroid.mytodo.features.auth.presentation.create_user_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.mytodo.features.auth.domain.user_cases.CreateUserUseCase
import net.wandroid.mytodo.features.common.ResultState

internal class CreateUserViewModel(
    private val createUserUseCase: CreateUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CreateUserState.Init)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CreateUserEffect>(replay = 0)
    val effect = _effect.asSharedFlow()

    fun dispatch(intent: CreateUserIntent) {
        when (intent) {
            is CreateUserIntent.RegisterUserIntent -> createUser(intent)
            is CreateUserIntent.UpdateRegistrationUserIntent -> updateRegistration(intent)
        }
    }

    private fun updateRegistration(intent: CreateUserIntent.UpdateRegistrationUserIntent) {
        // check for validations
        _state.update { createUserState ->
            createUserState.copy(
                userName = intent.userName,
                password = intent.password,
                rePassword = intent.rePassword,
            )
        }
    }

    private fun createUser(createUserIntent: CreateUserIntent.RegisterUserIntent) {

        if (!createUserIntent.validateUsernameAndPassword()) {
            _state.update { createUserState ->
                createUserState.copy(
                    error = "email or passwords doesn't match",
                    isLoading = false,
                )
            }
            return
        }

        viewModelScope.launch {
            createUserUseCase(
                createUserIntent.userName,
                createUserIntent.password
            ).collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { createUserState ->
                            createUserState.copy(
                                error = resultState.failData.message,
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> _state.update { it.copy(isLoading = true) }

                    is ResultState.Success -> {
                        _state.update { createUserState ->
                            createUserState.copy(
                                registrationCompleted = true,
                                error = null,
                                isLoading = false,
                            )
                        }
                        delay(1000L)
                        _effect.emit(CreateUserEffect.RegistrationCompletedEffect)
                    }
                }
            }
        }
    }
}

private fun CreateUserIntent.RegisterUserIntent.validateUsernameAndPassword(): Boolean {
    // TODO: Should be done in use case or in common
    // check if email is ok, should be a proper email pattern checker
    if (userName.isEmpty()) return false
    return password == rePassword
}
