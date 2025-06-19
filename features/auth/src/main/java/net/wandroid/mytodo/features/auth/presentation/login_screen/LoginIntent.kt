package net.wandroid.mytodo.features.auth.presentation.login_screen

internal sealed interface LoginIntent {
    data object RegisterIntent : LoginIntent
    data class LoginUserIntent(
        val userName: String,
        val password: String,
    ) : LoginIntent

    data class UpdateLoginIntent(
        val userName: String,
        val password: String,
    ) : LoginIntent

}