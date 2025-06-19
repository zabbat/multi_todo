package net.wandroid.mytodo.app.presentation.components

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kotlinx.coroutines.flow.StateFlow
import net.wandroid.mytodo.app.presentation.Routes
import net.wandroid.mytodo.features.auth.presentation.create_user_screen.CreateUserScreen
import net.wandroid.mytodo.features.auth.presentation.login_screen.LoginScreen
import net.wandroid.mytodo.features.todo.list.presentation.TodoListScreen

/**
 * Sets up the main navigation graph for the application.
 *
 * This graph includes routes for user authentication (login, create user) and
 * the main application content (e.g., Todo list). It utilizes [authComposable]
 * to protect routes that require authentication, based on the [isAuthedState].
 *
 * Navigation events triggered from within the screens (e.g., after login or registration)
 * are handled directly by the [navController] within this graph setup.
 *
 * @param navController The [NavController] used for navigating between destinations within this graph.
 * @param isAuthedState A [StateFlow] indicating whether the user is currently authenticated.
 *                      This is used by [authComposable] to determine which content to display
 *                      or whether to redirect unauthenticated users.
 */
fun NavGraphBuilder.setupMainGraph(
    navController: NavController,
    isAuthedState: StateFlow<Boolean>,
) {
    val popLoginToTop = {
        navController.navigate(Routes.Login) {
            popUpTo(Routes.Login) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    authComposable(
        route = Routes.CreateUser,
        isAuthedState = isAuthedState,
    ) {
        ScreenWithScaffold(navController = navController) {
            CreateUserScreen(
                onRegistrationCompleted = popLoginToTop,
            )
        }
    }
    authComposable(
        route = Routes.Login,
        isAuthedState = isAuthedState,
    ) {
        ScreenWithScaffold(navController = navController) {
            LoginScreen(
                onRegisterEvent = {
                    navController.navigate(Routes.CreateUser)
                },
                onLoggedInEvent = {
                    navController.navigate(Routes.Todo)
                })
        }
    }
    authComposable(
        route = Routes.Todo,
        isAuthedState = isAuthedState,
    ) {
        ScreenWithScaffold(
            navController = navController,
            onLogout = popLoginToTop,
        ) {
            TodoListScreen()
        }
    }
}