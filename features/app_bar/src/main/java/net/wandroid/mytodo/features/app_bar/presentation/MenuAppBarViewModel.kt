package net.wandroid.mytodo.features.app_bar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.mytodo.features.auth.UserAuthManager
import net.wandroid.mytodo.features.common.ResultState

internal class MenuAppBarViewModel(
    private val userAuthManager: UserAuthManager,
) : ViewModel() {
    private val _state = MutableStateFlow(MenuAppBarState.Init)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MenuTopBarEffect>(replay = 0)
    val effect = _effect.asSharedFlow()

    fun dispatch(intent: MenuAppBarIntent) {
        when (intent) {
            MenuAppBarIntent.LogoutIntent -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            userAuthManager.logout().collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { logOutAppBarState ->
                            logOutAppBarState.copy(
                                error = "failed to logout",
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> {
                        _state.update { logOutAppBarState ->
                            logOutAppBarState.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is ResultState.Success -> {
                        _state.update { logOutAppBarState ->
                            _effect.emit(MenuTopBarEffect.LoggedOutEffect)
                            logOutAppBarState.copy(
                                error = null,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
