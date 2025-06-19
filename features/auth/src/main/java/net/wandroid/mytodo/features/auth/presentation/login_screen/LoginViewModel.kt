package net.wandroid.mytodo.features.auth.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.mytodo.features.auth.domain.user_cases.LoginUserUseCase
import net.wandroid.mytodo.features.common.ResultState

internal class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState.Init)
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<LoginEffect>(replay = 0)
    val effect = _effect.asSharedFlow()

    fun dispatch(intent: LoginIntent) {
        when (intent) {
            LoginIntent.RegisterIntent -> registerUser()
            is LoginIntent.LoginUserIntent -> loginUser(intent)
            is LoginIntent.UpdateLoginIntent -> updateLogin(intent)
        }
    }

    private fun updateLogin(intent: LoginIntent.UpdateLoginIntent) {
        _state.update { loginState ->
            loginState.copy(
                userName = intent.userName,
                password = intent.password,
            )
        }
    }

    private fun loginUser(intent: LoginIntent.LoginUserIntent) {
        viewModelScope.launch {
            loginUserUseCase(
                username = intent.userName,
                password = intent.password,
            ).collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { loginState ->
                            loginState.copy(
                                error = "Error: ${resultState.failData.message}, e: ${resultState.failData.exception}",
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> _state.update { it.copy(isLoading = true) }
                    is ResultState.Success -> {
                        _state.update { loginState ->
                            loginState.copy(
                                error = null,
                                isLoading = false,
                            )
                        }
                        _effect.emit(LoginEffect.LoggedInEffect)
                    }
                }
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            _effect.emit(LoginEffect.RegisterEffect)
        }
    }

}