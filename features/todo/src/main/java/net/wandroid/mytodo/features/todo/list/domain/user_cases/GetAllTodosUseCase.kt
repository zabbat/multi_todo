package net.wandroid.mytodo.features.todo.list.domain.user_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.mappers.toTodoData
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository

internal class GetAllTodosUseCase(
    private val todoRepository: TodoRepository,
) {
    operator fun invoke(): Flow<ResultState<List<TodoData>>> = flow {
        emit(ResultState.Loading())
        try {
            val result = todoRepository.getAll().map { it.toTodoData() }
            emit(ResultState.Success(data = result))
        } catch (e: TodoRepository.RepositoryException) {
            Log.d("egg", "error: ${e.message}")
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