package net.wandroid.mytodo.features.todo.list.domain.user_cases

import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import org.junit.Test

class GetTodosUseCaseTest {
    private val todoRepository: TodoRepository = mockk()
    private val getTodosUseCase = GetTodosUseCase(todoRepository)


    @Test
    fun `emit Loading before Success result`() = runTest {
        //given
        coEvery { todoRepository.getUsersTodos() } returns mockk(relaxed = true)
        //when
        val results = getTodosUseCase()
            .toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Success::class.java)
    }

    @Test
    fun `emit Loading before Fail result`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.getUsersTodos() } throws e
        //when
        val results = getTodosUseCase().toList()
        //then
        Truth.assertThat(results[0]).isInstanceOf(ResultState.Loading::class.java)
        Truth.assertThat(results[1]).isInstanceOf(ResultState.Fail::class.java)
    }

    @Test
    fun `emit Fail on RepositoryException`() = runTest {
        //given
        val e = TodoRepository.RepositoryException("message")
        coEvery { todoRepository.getUsersTodos() } throws e
        //when
        val result = getTodosUseCase()
            .last() as ResultState.Fail
        //then
        Truth.assertThat(result.failData.message).isEqualTo("message")
        Truth.assertThat(result.failData.exception).isEqualTo(e)
    }

    @Test
    fun `emit Success with data on success`() = runTest {
        //given
        val todoResponseDto = TodoResponseDto(
            id = 1,
            title = "title",
            content = "content",
            isDone = true
        )
        coEvery { todoRepository.getUsersTodos() } returns listOf(todoResponseDto)
        //when
        val result = getTodosUseCase()
            .last() as ResultState.Success
        //then
        val expected = TodoData(
            id = 1,
            title = "title",
            content = "content",
            isDone = true
        )
        Truth.assertThat(result.data).isEqualTo(listOf(expected))
    }
}