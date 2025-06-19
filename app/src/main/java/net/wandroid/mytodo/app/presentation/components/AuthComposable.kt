package net.wandroid.mytodo.app.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.StateFlow
import net.wandroid.mytodo.R
import net.wandroid.mytodo.app.presentation.Routes
import net.wandroid.mytodo.app.presentation.LocalRouteContext
import net.wandroid.mytodo.app.presentation.RouteContext

/**
 * A composable function for building navigation graph entries that require authentication.
 *
 * It observes the [isAuthedState] to determine if the user is authenticated.
 * If the route does not require authentication it will always show [content].
 * See [Routes.requireAuth].
 * If authentication is required and [isAuthedState] emitted true it displays the [content].
 * Otherwise, it displays the [unauthorizedContent].
 *
 * @param T The type of the route, constrained to be a subclass of [Routes].
 * @param route The specific route destination of type [T].
 * @param isAuthedState A [StateFlow] indicating whether the user is currently authenticated.
 * @param unauthorizedContent A composable lambda that defines the UI to be displayed
 *                            when the user is not authenticated. This lambda takes no parameters
 *                            and is expected to render UI. Defaults to a predefined screen if not provided.
 * @param content A composable lambda that defines the UI to be displayed
 *                when the user is authenticated. This lambda takes no parameters
 *                and is expected to render UI.
 */
inline fun <reified T : Routes> NavGraphBuilder.authComposable(
    route: T,
    isAuthedState: StateFlow<Boolean>,
    crossinline unauthorizedContent: @Composable () -> Unit = {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.unauthorized),
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    },
    crossinline content: @Composable () -> Unit,
) {
    composable<T> { backStackEntry ->
        val context = RouteContext(route = route, backStackEntry = backStackEntry)
        CompositionLocalProvider(LocalRouteContext provides context) {
            val isAuthed by isAuthedState.collectAsStateWithLifecycle()
            if (route.requireAuth && !isAuthed) {
                // TODO: navigation - should be an event/action instead so it navigates to a screen
                unauthorizedContent()
            } else {
                content()
            }
        }
    }
}
