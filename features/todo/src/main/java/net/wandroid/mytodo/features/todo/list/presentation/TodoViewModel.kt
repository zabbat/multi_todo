package net.wandroid.mytodo.features.todo.list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.TodoRequestData
import net.wandroid.mytodo.features.todo.list.domain.user_cases.AddTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.DeleteTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.GetTodosUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.UpdateTodoUseCase

internal class TodoViewModel(
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getTodosUseCase: GetTodosUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState.Init)
    val state = _state.asStateFlow()

    fun dispatch(intent: TodoListIntent) {
        when (intent) {
            TodoListIntent.InitializeIntent -> {
                if (savedStateHandle.get<Boolean>(KEY_INITIALIZED) != true) {
                    savedStateHandle[KEY_INITIALIZED] = true
                    getTodos()
                }
            }

            TodoListIntent.RefreshIntent -> getTodos()
            is TodoListIntent.UpdateTodoItemIntent -> updateTodo(
                id = intent.id,
                isChecked = intent.isChecked,
            )

            is TodoListIntent.AddTodoIntent -> addTodo(intent.title, intent.content)
            is TodoListIntent.ShowAddDialogIntent -> {
                _state.update { todoListState ->
                    todoListState.copy(
                        showAddDialog = intent.show,
                    )
                }
            }

            is TodoListIntent.DeleteItemIntent -> deleteTodo(intent.id, intent.onError)

        }
    }

    private fun getTodos() {
        viewModelScope.launch {
            getTodosUseCase().collect { resultState ->

                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { todoListState ->
                            todoListState.copy(
                                todos = emptyList(),
                                errorType = resultState.failData.exception,
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> {
                        _state.updateLoading(true)
                    }

                    is ResultState.Success -> {
                        _state.update { todoListState ->
                            todoListState.copy(
                                todos = resultState.data,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }


    private fun updateTodo(
        id: Long,
        isChecked: Boolean,
    ) {
        viewModelScope.launch {
            updateTodoUseCase(
                TodoRequestData(
                    id = id,
                    isDone = isChecked,
                )
            ).collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { todoListState ->
                            todoListState.copy(
                                errorType = resultState.failData.exception,
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> _state.updateLoading(true)
                    is ResultState.Success -> {
                        _state.update { todoListState ->
                            val editedList =
                                todoListState.todos.map {
                                    if (it.id == id) TodoData(
                                        id = id,
                                        title = it.title,
                                        content = it.content,
                                        isDone = isChecked,
                                    ) else it
                                }
                            todoListState.copy(
                                todos = editedList,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteTodo(id: Long, onError: () -> Unit) {
        viewModelScope.launch {
            deleteTodoUseCase(id).collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {

                        _state.update { todoListState ->
                            onError()
                            todoListState.copy(
                                isLoading = false
                            )

                        }
                    }

                    is ResultState.Loading -> _state.updateLoading(true)
                    is ResultState.Success -> {
                        _state.update { todoListState ->
                            val deletedTodo = todoListState.todos.find { it.id == id }
                            todoListState.copy(
                                todos = if (deletedTodo == null) {
                                    todoListState.todos
                                } else {
                                    todoListState.todos - deletedTodo
                                },
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addTodo(title: String, content: String) {
        viewModelScope.launch {
            addTodoUseCase(
                title = title,
                content = content,
            ).collect { resultState ->
                when (resultState) {
                    is ResultState.Fail -> {
                        _state.update { todoListState ->
                            todoListState.copy(
                                errorType = resultState.failData.exception,
                                isLoading = false,
                            )
                        }
                    }

                    is ResultState.Loading -> _state.updateLoading(true)
                    is ResultState.Success -> {
                        _state.update { todoListState ->
                            todoListState.copy(
                                todos = todoListState.todos + resultState.data,
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun MutableStateFlow<TodoListState>.updateLoading(isLoading: Boolean) =
        update { todoListState ->
            todoListState.copy(
                isLoading = isLoading,
            )
        }

    companion object {
        private const val KEY_INITIALIZED = "is_initialized"
    }
}