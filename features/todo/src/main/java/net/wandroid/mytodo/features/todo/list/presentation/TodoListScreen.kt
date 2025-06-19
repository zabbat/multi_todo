package net.wandroid.mytodo.features.todo.list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.wandroid.mytodo.features.todo.list.presentation.components.TodoListComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
) {
    val todoViewModel: TodoViewModel = koinViewModel()
    val state by todoViewModel.state.collectAsState()

    // Make sure the screen tried to load data at first view
    LaunchedEffect(Unit) {
        todoViewModel.dispatch(TodoListIntent.InitializeIntent)
    }

    TodoListComponent(
        todoListState = state,
        modifier = modifier,
        onRefresh = { todoViewModel.dispatch(intent = TodoListIntent.RefreshIntent) },
        onItemChanged = { id, newCheckedState ->
            todoViewModel.dispatch(
                intent = TodoListIntent.UpdateTodoItemIntent(
                    id = id,
                    isChecked = newCheckedState,
                )
            )
        },
        onAddClicked = { title: String, content: String ->
            todoViewModel.dispatch(
                intent = TodoListIntent.AddTodoIntent(title, content)
            )
        },
        onShowAddDialog = { show ->
            todoViewModel.dispatch(intent = TodoListIntent.ShowAddDialogIntent(show))
        },
        onItemDismissed = { id, onError ->
            todoViewModel.dispatch(intent = TodoListIntent.DeleteItemIntent(id, onError))
        }
    )
}