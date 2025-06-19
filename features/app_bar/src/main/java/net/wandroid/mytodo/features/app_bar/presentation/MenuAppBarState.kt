package net.wandroid.mytodo.features.app_bar.presentation

internal data class MenuAppBarState(
    val isLoading: Boolean,
    val error: String?
) {
    companion object {
        val Init = MenuAppBarState(
            isLoading = false,
            error = null,
        )
    }
}