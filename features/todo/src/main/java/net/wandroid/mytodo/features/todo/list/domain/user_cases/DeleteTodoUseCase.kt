package net.wandroid.mytodo.features.todo.list.domain.user_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository

internal class DeleteTodoUseCase(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(
        id: Long,
    ): Flow<ResultState<Long>> = flow {
        emit(ResultState.Loading())
        try {
            val result = todoRepository.deleteTodo(id)
            emit(ResultState.Success(data = result))
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