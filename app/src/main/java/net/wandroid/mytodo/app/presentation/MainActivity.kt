package net.wandroid.mytodo.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import net.wandroid.mytodo.app.presentation.components.setupMainGraph
import net.wandroid.mytodo.app.ui.theme.MyTodoTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyTodoTheme {
                val viewModel = koinViewModel<MainViewModel>()
                val navController = rememberNavController()
                NavHost(
                    startDestination = Routes.Login,
                    navController = navController,
                ) {
                    setupMainGraph(
                        navController = navController,
                        isAuthedState = viewModel.isLoggedInState,
                    )
                }
            }
        }
    }
}

