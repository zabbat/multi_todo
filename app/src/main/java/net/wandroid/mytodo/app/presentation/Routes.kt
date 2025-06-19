package net.wandroid.mytodo.app.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {
    val hasMenu: Boolean
    val requireAuth: Boolean
        get() = true
    val canNavigateUp: Boolean
        get() = true

    @Serializable
    private sealed interface HasMenuDestination : Routes {
        override val hasMenu: Boolean
            get() = true
    }

    @Serializable
    private sealed interface NoMenuDestination : Routes {
        override val hasMenu: Boolean
            get() = false
    }

    @Serializable
    private sealed interface NoAuthRequired : Routes {
        override val requireAuth: Boolean
            get() = false
    }

    @Serializable
    private sealed interface RootDestination : Routes {
        override val canNavigateUp: Boolean
            get() = false
    }

    @Serializable
    data object Login : NoMenuDestination, NoAuthRequired, RootDestination

    @Serializable
    data object CreateUser : NoMenuDestination, NoAuthRequired

    @Serializable
    data object Todo : HasMenuDestination, RootDestination

}


