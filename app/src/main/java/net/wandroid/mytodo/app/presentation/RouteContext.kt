package net.wandroid.mytodo.app.presentation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavBackStackEntry

data class RouteContext(
    val route: Routes,
    val backStackEntry: NavBackStackEntry,
)

val LocalRouteContext = compositionLocalOf<RouteContext> {
    error("No RouteContext provided")
}