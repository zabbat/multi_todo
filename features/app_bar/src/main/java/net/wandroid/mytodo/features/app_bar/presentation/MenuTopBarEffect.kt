package net.wandroid.mytodo.features.app_bar.presentation

internal sealed interface MenuTopBarEffect {
    data object LoggedOutEffect : MenuTopBarEffect
}
