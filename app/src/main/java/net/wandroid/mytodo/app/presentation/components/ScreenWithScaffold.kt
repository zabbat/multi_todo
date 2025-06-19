package net.wandroid.mytodo.app.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.wandroid.mytodo.R
import net.wandroid.mytodo.app.presentation.LocalRouteContext
import net.wandroid.mytodo.features.app_bar.presentation.components.MenuTopBar


/**
 * A wrapper composable that provides a common screen structure using a [Scaffold].
 *
 * This scaffold dynamically configures its top app bar ([MenuTopBar]) based on metadata
 * retrieved from the current route via [LocalRouteContext]. Specifically, it checks
 * [Routes.hasMenu] to decide if the [MenuTopBar] should be displayed, and
 * [Routes.canNavigateUp] to configure the navigation icon within the [MenuTopBar].
 *
 * The main screen content is placed within the body of the scaffold, with appropriate
 * padding applied to avoid overlapping the top app bar.
 *
 * @param navController The [NavController] passed to the [MenuTopBar] for handling
 *                      navigation actions, such as back navigation.
 * @param onLogout A lambda function to be invoked when the logout action is triggered
 *                 from the [LogoutTopBar]. This is passed down to the [LogoutTopBar].
 *                 Defaults to an empty lambda.
 * @param content A composable lambda defining the main content of the screen.
 *                This content is placed inside a [Box] that respects the scaffold's
 *                inner padding.
 */
@Composable
fun ScreenWithScaffold(
    navController: NavController,
    onLogout: () -> Unit = {},
    content: @Composable () -> Unit,
) {

    val currentRoute = LocalRouteContext.current.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentRoute.hasMenu)
                MenuTopBar(
                    title = stringResource(R.string.menu_title_todo),
                    showNavigation = currentRoute.canNavigateUp,
                    onLoggedOut = onLogout,
                    navController = navController,
                )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}