package net.wandroid.mytodo.features.todo.list.domain.user_cases

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoRequestData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import org.junit.Test

class UpdateTodoUseCaseTest {

    private val todoRepository: TodoRepository = mockk()
    private val updateTodoUseCase = UpdateTodoUseCase(todoRepository)


    @Test
    fun `emit Loading before Success result`() = runTest {
        //given
        coEvery { todoRepository.updateTodo(any()) } returns mockk(relaxed = true)
        //when
        val results = updateTodoUseCase(
            mockk(relaxed = true)
        ).toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Success::class.java)
    }

    @Test
    fun `emit Loading before Fail result`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.updateTodo(any()) } throws e
        //when
        val results = updateTodoUseCase(
            mockk(relaxed = true),
        ).toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Fail::class.java)
    }

    @Test
    fun `emit Fail on RepositoryException`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.updateTodo(any()) } throws e
        //when
        val result = updateTodoUseCase(
            mockk(relaxed = true)
        ).last() as ResultState.Fail
        //then
        Truth.assertThat(result.failData.message).isEqualTo("message")
        Truth.assertThat(result.failData.exception).isEqualTo(e)
    }

    @Test
    fun `emit Success with Unit data on success`() = runTest {
        //given
        val todoResponseDto = TodoResponseDto(
            id = 1,
            title = "title",
            content = "content",
            isDone = true,
        )
        val todoRequestData = TodoRequestData(
            id = 1,
            title = "title",
            content = "content",
            isDone = true,
        )
        coEvery { todoRepository.updateTodo(any()) } returns todoResponseDto
        //when
        val result = updateTodoUseCase(
            todoRequestData,
        ).last() as ResultState.Success
        //then
        Truth.assertThat(result.data).isEqualTo(Unit)
    }
}