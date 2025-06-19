package net.wandroid.mytodo.features.todo.list.presentation

internal sealed interface TodoListIntent {
    data object InitializeIntent : TodoListIntent
    data object RefreshIntent : TodoListIntent
    data class UpdateTodoItemIntent(val id: Long, val isChecked: Boolean) : TodoListIntent
    data class AddTodoIntent(val title: String, val content: String) : TodoListIntent
    data class ShowAddDialogIntent(val show: Boolean) : TodoListIntent
    data class DeleteItemIntent(val id: Long, val onError: () -> Unit) : TodoListIntent
}