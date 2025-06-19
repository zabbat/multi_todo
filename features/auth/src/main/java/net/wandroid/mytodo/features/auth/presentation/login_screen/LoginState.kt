package net.wandroid.mytodo.features.auth.presentation.login_screen

internal data class LoginState(
    val userName: String,
    val password: String,
    val isLoading: Boolean,
    val error: String?,
) {
    companion object {
        val Init = LoginState(
            userName = "",
            password = "",
            isLoading = false,
            error = null,
        )
    }
}
