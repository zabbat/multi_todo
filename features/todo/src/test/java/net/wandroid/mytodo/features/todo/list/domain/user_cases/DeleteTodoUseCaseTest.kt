package net.wandroid.mytodo.features.todo.list.domain.user_cases

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import org.junit.Test


class DeleteTodoUseCaseTest {
    private val todoRepository: TodoRepository = mockk()
    private val deleteTodoUseCase = DeleteTodoUseCase(todoRepository = todoRepository)
    private val id = 1L

    @Test
    fun `emit Loading before Success result`() = runTest {
        //given
        coEvery { todoRepository.deleteTodo(any()) } returns id
        //when
        val results = deleteTodoUseCase(
            id = id,
        ).toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Success::class.java)
    }

    @Test
    fun `emit Loading before Fail result`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.deleteTodo(id) } throws e
        //when
        val results = deleteTodoUseCase(
            id = id,
        ).toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Fail::class.java)
    }

    @Test
    fun `emit Fail on RepositoryException`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.deleteTodo(id) } throws e
        //when
        val result = deleteTodoUseCase(
            id = id,
        ).last() as ResultState.Fail
        //then
        Truth.assertThat(result.failData.message).isEqualTo("message")
        Truth.assertThat(result.failData.exception).isEqualTo(e)
    }

    @Test
    fun `emit Success with deleted id on success`() = runTest {
        //given
        coEvery { todoRepository.deleteTodo(any()) } returns id
        //when
        val result = deleteTodoUseCase(
            id = id,
        ).last() as ResultState.Success
        //then
        Truth.assertThat(result.data).isEqualTo(id)
    }
}