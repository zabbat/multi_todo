package net.wandroid.mytodo.features.auth.presentation.login_screen

internal sealed interface LoginEffect {
    data object RegisterEffect : LoginEffect
    data object LoggedInEffect : LoginEffect
}