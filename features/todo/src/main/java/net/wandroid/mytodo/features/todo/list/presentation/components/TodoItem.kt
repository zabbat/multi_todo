package net.wandroid.mytodo.features.todo.list.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData

@Composable
internal fun TodoItem(
    todoData: TodoData,
    onItemChecked: (id: Long, isDone: Boolean) -> Unit,
    onItemDismissed: (id: Long, onError: () -> Unit) -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    if (isVisible) {
        AnimatedVisibility(
            visible = isVisible,
            exit = shrinkVertically() + fadeOut()
        ) {
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    when (dismissValue) {
                        SwipeToDismissBoxValue.StartToEnd -> false

                        SwipeToDismissBoxValue.EndToStart -> {
                            isVisible = false
                            onItemDismissed(todoData.id) {
                                isVisible = true
                            }
                            true
                        }

                        SwipeToDismissBoxValue.Settled -> false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = { DeleteBackground(dismissState) },
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(8.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = todoData.title,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = todoData.content,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )

                        Checkbox(
                            checked = todoData.isDone,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .testTag("checkBox"),
                            onCheckedChange = { onItemChecked(todoData.id, !todoData.isDone) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTodoItem() {
    val todoData = TodoData(
        id = 0,
        title = "title",
        content = "content",
        isDone = false,
    )
    TodoItem(
        todoData = todoData,
        onItemChecked = { _, _ -> },
        onItemDismissed = { _, _ -> },
    )
}