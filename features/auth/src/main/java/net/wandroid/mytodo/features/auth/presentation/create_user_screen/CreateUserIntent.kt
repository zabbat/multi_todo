package net.wandroid.mytodo.features.auth.presentation.create_user_screen

internal sealed interface CreateUserIntent {
    data class RegisterUserIntent(
        val userName: String,
        val password: String,
        val rePassword: String,
    ) : CreateUserIntent

    data class UpdateRegistrationUserIntent(
        val userName: String,
        val password: String,
        val rePassword: String,
    ) : CreateUserIntent

}