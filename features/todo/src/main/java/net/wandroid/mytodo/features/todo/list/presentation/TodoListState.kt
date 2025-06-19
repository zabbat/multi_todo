package net.wandroid.mytodo.features.todo.list.presentation

import net.wandroid.mytodo.features.todo.list.domain.model.TodoData

internal data class TodoListState(
    val todos: List<TodoData>,
    val isLoading: Boolean,
    val errorType: Throwable?,
    val showAddDialog: Boolean,
) {
    companion object {
        val Init = TodoListState(
            todos = emptyList(),
            isLoading = false,
            errorType = null,
            showAddDialog = false,
        )
    }
}
