package net.wandroid.mytodo.features.todo.list.domain.user_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.mappers.toTodoData
import net.wandroid.mytodo.features.todo.list.data.remote.TodoAddRequestDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository

internal class AddTodoUseCase(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(
        title: String,
        content: String
    ): Flow<ResultState<TodoData>> = flow {
        emit(ResultState.Loading())
        try {
            val todoAddRequestDto = TodoAddRequestDto(
                title = title,
                content = content,
            )
            val result = todoRepository.addTodo(todoAddRequestDto)
            emit(ResultState.Success(data = result.toTodoData()))
        } catch (e: TodoRepository.RepositoryException) {
            emit(
                ResultState.Fail(
                    failData = FailData(
                        message = "${e.message}",
                        exception = e,
                    )
                )
            )
        }
    }
}