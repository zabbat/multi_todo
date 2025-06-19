package net.wandroid.mytodo.features.todo.list.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.wandroid.mytodo.features.todo.R

@Composable
internal fun AddTodoDialog(
    onEvent: (event: AddDialogEvent) -> Unit,
) {
    Dialog(
        onDismissRequest = { onEvent(AddDialogEvent.DismissEvent) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                var titleText by remember { mutableStateOf("") }
                var contentText by remember { mutableStateOf("") }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(stringResource(R.string.todo_add_title)) },
                    value = titleText,
                    onValueChange = { text ->
                        titleText = text
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = { Text(stringResource(R.string.todo_add_content)) },
                    value = contentText,
                    onValueChange = { text ->
                        contentText = text
                    }
                )
                Row {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = {
                            onEvent(AddDialogEvent.SaveTodoEvent(titleText, contentText))
                        }
                    ) {
                        Text(stringResource(R.string.todo_add_button))
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        onClick = {
                            onEvent(AddDialogEvent.DismissEvent)
                        }
                    ) {
                        Text(stringResource(R.string.todo_add_cancel))
                    }
                }
            }
        }
    }
}

sealed interface AddDialogEvent {
    data object DismissEvent : AddDialogEvent
    data class SaveTodoEvent(val title: String, val content: String) : AddDialogEvent
}

@Composable
@Preview
fun PreviewAddTodoDialog() {
    AddTodoDialog(
        onEvent = {}
    )
}