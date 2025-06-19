package net.wandroid.mytodo.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.mytodo.features.auth.UserAuthManager

internal class MainViewModel(
    private val userAuthManager: UserAuthManager,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState.Init)
    private val state = _state.asStateFlow()

    val isLoggedInState: StateFlow<Boolean> = state.map { it.isUserLoggedIn }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = state.value.isUserLoggedIn,
    )

    init {
        viewModelScope.launch {
            userAuthManager.loginStatus().collect { userLoginStatus ->

                when (userLoginStatus) {
                    UserAuthManager.UserLoginStatus.Invalid -> {
                        _state.update { mainState ->
                            mainState.copy(
                                isUserLoggedIn = false
                            )
                        }
                    }

                    UserAuthManager.UserLoginStatus.Valid -> {
                        _state.update { mainState ->
                            mainState.copy(
                                isUserLoggedIn = true
                            )
                        }
                    }
                }
            }
        }
    }
}