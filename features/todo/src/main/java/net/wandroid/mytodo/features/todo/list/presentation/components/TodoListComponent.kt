package net.wandroid.mytodo.features.todo.list.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.wandroid.mytodo.features.todo.R
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.presentation.TodoListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoListComponent(
    todoListState: TodoListState,
    modifier: Modifier,
    onRefresh: () -> Unit,
    onItemChanged: (id: Long, newCheckedState: Boolean) -> Unit,
    onAddClicked: (title: String, content: String) -> Unit,
    onShowAddDialog: (show: Boolean) -> Unit,
    onItemDismissed: (id: Long, onError: () -> Unit) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        PullToRefreshBox(
            isRefreshing = todoListState.isLoading,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
        ) {
            LazyColumn {
                items(todoListState.todos, key = { it.id }) { item ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (todoListState.errorType != null) {
                            Text(
                                stringResource(
                                    R.string.list_error,
                                    todoListState.errorType.message ?: "Unknown"
                                )
                            )
                        } else {
                            TodoItem(
                                todoData = item,
                                onItemChecked = onItemChanged,
                                onItemDismissed = onItemDismissed,
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { onShowAddDialog(true) },
        ) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
        }
        if (todoListState.showAddDialog) {
            AddTodoDialog(
                onEvent = { event ->
                    when (event) {
                        AddDialogEvent.DismissEvent -> onShowAddDialog(false)
                        is AddDialogEvent.SaveTodoEvent -> {
                            onAddClicked(event.title, event.content)
                            onShowAddDialog(false)
                        }
                    }
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun PreviewTodoListComponent(

) {
    val todoListState = TodoListState.Init.copy(
        todos = listOf(
            TodoData(
                id = 1L,
                "title title title",
                content = "content content content",
                isDone = false,
            )
        )
    )
    TodoListComponent(
        todoListState = todoListState,
        modifier = Modifier,
        onRefresh = {},
        onItemChanged = { _, _ -> },
        onAddClicked = { _, _ -> },
        onShowAddDialog = {},
        onItemDismissed = { _, _ -> },
    )
}