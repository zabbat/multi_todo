package net.wandroid.mytodo.features.auth.presentation.create_user_screen

internal sealed interface CreateUserEffect {
    data object RegistrationCompletedEffect : CreateUserEffect
}