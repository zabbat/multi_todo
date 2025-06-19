package net.wandroid.mytodo.features.app_bar.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import net.wandroid.mytodo.features.app_bar.R
import net.wandroid.mytodo.features.app_bar.presentation.MenuAppBarIntent
import net.wandroid.mytodo.features.app_bar.presentation.MenuAppBarViewModel
import net.wandroid.mytodo.features.app_bar.presentation.MenuAppBarState
import net.wandroid.mytodo.features.app_bar.presentation.MenuTopBarEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    title: String,
    showNavigation: Boolean,
    onLoggedOut: () -> Unit,
) {
    val viewModel: MenuAppBarViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        viewModel.effect.flowWithLifecycle(
            lifecycle = lifecycle.lifecycle,
            minActiveState = Lifecycle.State.STARTED
        ).collect { event ->
            when (event) {
                MenuTopBarEffect.LoggedOutEffect -> onLoggedOut()
            }
        }
    }
    val hasBackStack = navController.previousBackStackEntry != null
    MenuTopBarComponent(
        showNavigation = showNavigation && hasBackStack,
        modifier = modifier,
        title = title,
        state = state,
        onLogout = { viewModel.dispatch(MenuAppBarIntent.LogoutIntent) },
        onBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MenuTopBarComponent(
    showNavigation: Boolean,
    modifier: Modifier = Modifier,
    title: String,
    state: MenuAppBarState,
    onLogout: () -> Unit,
    onBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        var expanded by remember { mutableStateOf(false) }
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = {
                Text(title)
            },
            navigationIcon = {
                if (showNavigation) {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                } else {
                    // nothing
                }
            },
            windowInsets = WindowInsets.statusBars,
            actions = {
                IconButton(
                    onClick = {
                        expanded = true
                    },
                ) {
                    Icon(
                        imageVector = if (state.isLoading) Icons.Default.Refresh else Icons.Default.MoreVert,
                        contentDescription = null,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.logout),
                                )
                            },
                            onClick = {
                                expanded = false
                                onLogout()
                            },
                        )
                    }
                }
            },
        )
    }
}

@Composable
@Preview
fun PreviewMenuTopBarComponent() {
    MenuTopBarComponent(
        showNavigation = true,
        modifier = Modifier,
        title = "The Title",
        state = MenuAppBarState.Init,
        onLogout = {},
        onBack = {},
    )
}
