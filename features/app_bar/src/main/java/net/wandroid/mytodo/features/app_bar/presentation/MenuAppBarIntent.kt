package net.wandroid.mytodo.features.app_bar.presentation

internal sealed interface MenuAppBarIntent {
    data object LogoutIntent : MenuAppBarIntent
}