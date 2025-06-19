package net.wandroid.mytodo.features.todo.list.domain.model.repositories

import net.wandroid.mytodo.features.todo.list.data.remote.TodoAddRequestDto
import net.wandroid.mytodo.features.todo.list.data.remote.TodoPatchRequestDto
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto

internal interface TodoRepository {
    @Throws(RepositoryException::class)
    suspend fun getAll(): List<TodoResponseDto>

    @Throws(RepositoryException::class)
    suspend fun getUsersTodos(): List<TodoResponseDto>

    @Throws(RepositoryException::class)
    suspend fun updateTodo(todoPatchRequestDto: TodoPatchRequestDto): TodoResponseDto

    @Throws(RepositoryException::class)
    suspend fun addTodo(todoAddRequestDto: TodoAddRequestDto): TodoResponseDto

    @Throws(RepositoryException::class)
    suspend fun deleteTodo(id: Long): Long

    class RepositoryException(message: String) : Throwable(message)
}