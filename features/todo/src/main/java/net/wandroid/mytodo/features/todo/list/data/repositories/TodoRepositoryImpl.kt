package net.wandroid.mytodo.features.todo.list.data.repositories

import net.wandroid.mytodo.features.todo.list.data.remote.TodoAddRequestDto
import net.wandroid.mytodo.features.todo.list.data.remote.TodoApi
import net.wandroid.mytodo.features.todo.list.data.remote.TodoPatchRequestDto
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import java.io.IOException

internal class TodoRepositoryImpl(
    private val todoApi: TodoApi,
) : TodoRepository {
    override suspend fun getAll(): List<TodoResponseDto> {
        return try {
            todoApi.getAllTodos()
                ?: throw TodoRepository.RepositoryException("failed to get all todos")
        } catch (e: IOException) {
            throw TodoRepository.RepositoryException(e.message ?: "Unknown")
        }
    }

    override suspend fun getUsersTodos(): List<TodoResponseDto> {
        return try {
            todoApi.getTodos()
                ?: throw TodoRepository.RepositoryException("failed to list todos")
        } catch (e: IOException) {
            throw TodoRepository.RepositoryException(e.message ?: "Unknown")
        }
    }

    override suspend fun updateTodo(todoPatchRequestDto: TodoPatchRequestDto): TodoResponseDto {
        return try {
            todoApi.updateTodo(todoPatchRequestDto)
                ?: throw TodoRepository.RepositoryException("failed to update dto: $todoPatchRequestDto")
        } catch (e: IOException) {
            throw TodoRepository.RepositoryException(e.message ?: "Unknown")
        }
    }

    override suspend fun addTodo(todoAddRequestDto: TodoAddRequestDto): TodoResponseDto {
        return try {
            todoApi.addTodo(todoAddRequestDto)
                ?: throw TodoRepository.RepositoryException("failed to add dto: $todoAddRequestDto")
        } catch (e: IOException) {
            throw TodoRepository.RepositoryException(e.message ?: "Unknown")
        }
    }

    override suspend fun deleteTodo(id: Long): Long {
        return try {
            todoApi.deleteTodo(id)
        } catch (e: IOException) {
            throw TodoRepository.RepositoryException(e.message ?: "Unknown")
        }
    }
}