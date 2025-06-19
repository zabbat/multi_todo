package net.wandroid.mytodo.features.todo.list.domain.user_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.mappers.toTodoPatchRequestDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoRequestData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository

internal class UpdateTodoUseCase(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(todoRequestData: TodoRequestData): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading())
        try {
            todoRepository.updateTodo(todoRequestData.toTodoPatchRequestDto())
            emit(ResultState.Success(data = Unit))
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