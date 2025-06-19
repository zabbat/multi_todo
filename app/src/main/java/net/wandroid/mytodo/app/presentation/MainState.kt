package net.wandroid.mytodo.app.presentation

internal data class MainState(
    val isUserLoggedIn: Boolean,
) {
    companion object {
        val Init = MainState(
            isUserLoggedIn = true,
        )
    }
}