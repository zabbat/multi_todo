package net.wandroid.mytodo.features.auth.presentation.create_user_screen

internal data class CreateUserState(
    val userName: String,
    val password: String,
    val rePassword: String,
    val registrationCompleted: Boolean,
    val isLoading: Boolean,
    val error: String?,
) {
    companion object {
        val Init = CreateUserState(
            userName = "",
            password = "",
            rePassword = "",
            registrationCompleted = false,
            isLoading = false,
            error = null,
        )
    }
}