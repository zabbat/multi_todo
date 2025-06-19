package net.wandroid.mytodo.features.todo.list.presentation

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.wandroid.mytodo.features.common.FailData
import net.wandroid.mytodo.features.common.ResultState
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.TodoRequestData
import net.wandroid.mytodo.features.todo.list.domain.user_cases.AddTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.DeleteTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.GetTodosUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.UpdateTodoUseCase
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val addTodoUseCase: AddTodoUseCase = mockk()
    private val deleteTodoUseCase: DeleteTodoUseCase = mockk()
    private val getTodosUseCase: GetTodosUseCase = mockk()
    private val updateTodoUseCase: UpdateTodoUseCase = mockk()

    private val todoData = TodoData(
        id = 1L,
        title = "title",
        content = "content",
        isDone = false,
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        //MockKAnnotations.init()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `call getTodosUseCase when dispatch with RefreshIntent`() = runTest {
        //given
        coEvery { getTodosUseCase() } returns mockk(relaxed = true)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.RefreshIntent)
        //then
        testScheduler.advanceUntilIdle()
        coVerify { getTodosUseCase() }
    }

    @Test
    fun `update state with todos when successfully dispatch with RefreshIntent`() = runTest {
        //given
        val todos = listOf(todoData)
        val resultState = ResultState.Success(todos)
        coEvery { getTodosUseCase() } returns flowOf(resultState)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.RefreshIntent)
        //then
        testScheduler.advanceUntilIdle()
        Truth.assertThat(viewModel.state.value).isEqualTo(
            TodoListState(
                todos = todos,
                isLoading = false,
                errorType = null,
                showAddDialog = false,
            )
        )
    }

    @Test
    fun `update state with error when failed to dispatch with RefreshIntent`() = runTest {
        //given
        val exception = mockk<Exception>(relaxed = true)
        val failData = FailData(
            message = "message",
            exception = exception,
        )
        coEvery { getTodosUseCase() } returns flowOf(ResultState.Fail(failData))
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.RefreshIntent)
        //then
        testScheduler.advanceUntilIdle()
        Truth.assertThat(viewModel.state.value).isEqualTo(
            TodoListState(
                todos = emptyList(),
                isLoading = false,
                errorType = exception,
                showAddDialog = false,
            )
        )
    }

    @Test
    fun `call getTodosUseCase when dispatch with InitializeIntent for the first time`() = runTest {
        //given
        coEvery { getTodosUseCase() } returns mockk(relaxed = true)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.InitializeIntent)
        //then
        testScheduler.advanceUntilIdle()
        coVerify { getTodosUseCase() }
    }

    @Test
    fun `do not call getTodosUseCase when dispatch with InitializeIntent after the first time`() =
        runTest {
            //given
            coEvery { getTodosUseCase() } returns mockk(relaxed = true)
            val viewModel = givenAViewModel()
            viewModel.dispatch(TodoListIntent.InitializeIntent)
            //when
            viewModel.dispatch(TodoListIntent.InitializeIntent)
            //then
            testScheduler.advanceUntilIdle()
            coVerify(exactly = 1) { getTodosUseCase() }
        }

    @Test
    fun `update state with error when failed to dispatch with InitializeIntent`() = runTest {
        //given
        val exception = mockk<Exception>(relaxed = true)
        val failData = FailData(
            message = "message",
            exception = exception,
        )
        coEvery { getTodosUseCase() } returns flowOf(ResultState.Fail(failData))
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.InitializeIntent)
        //then
        testScheduler.advanceUntilIdle()
        Truth.assertThat(viewModel.state.value).isEqualTo(
            TodoListState(
                todos = emptyList(),
                isLoading = false,
                errorType = exception,
                showAddDialog = false,
            )
        )
    }

    @Test
    fun `call getTodosUseCase when dispatch with AddTodoIntent`() = runTest {
        //given
        val title = "title"
        val content = "content"
        coEvery { addTodoUseCase(title, content) } returns mockk(relaxed = true)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.AddTodoIntent(title, content))
        //then
        testScheduler.advanceUntilIdle()
        coVerify { addTodoUseCase(title, content) }
    }

    @Test
    fun `update state with todos when successfully dispatch with AddTodoIntent`() = runTest {
        //given
        coEvery {
            addTodoUseCase(
                "title",
                "content"
            )
        } returns flowOf(
            ResultState.Success(
                todoData
            )
        )
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.AddTodoIntent("title", "content"))
        //then
        testScheduler.advanceUntilIdle()
        Truth.assertThat(viewModel.state.value).isEqualTo(
            TodoListState(
                todos = listOf(todoData),
                isLoading = false,
                errorType = null,
                showAddDialog = false,
            )
        )
    }

    @Test
    fun `update state with error when failed to dispatch with AddTodoIntent`() = runTest {
        //given
        val exception = mockk<Exception>(relaxed = true)
        val failData = FailData(
            message = "message",
            exception = exception,
        )
        coEvery { addTodoUseCase(any(), any()) } returns flowOf(ResultState.Fail(failData))
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.AddTodoIntent("title", "content"))
        //then
        testScheduler.advanceUntilIdle()
        Truth.assertThat(viewModel.state.value).isEqualTo(
            TodoListState(
                todos = emptyList(),
                isLoading = false,
                errorType = exception,
                showAddDialog = false,
            )
        )
    }


    @Test
    fun `call updateTodoUseCase when dispatch with UpdateTodoItemIntent`() = runTest {
        //given
        coEvery { updateTodoUseCase(any()) } returns mockk(relaxed = true)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.UpdateTodoItemIntent(0L, true))
        //then
        testScheduler.advanceUntilIdle()
        coVerify {
            updateTodoUseCase(
                TodoRequestData(
                    id = 0,
                    isDone = true,
                )
            )
        }
    }

    @Test
    fun `call updateTodoUseCase when dispatch with DeleteItemIntent`() = runTest {
        //given
        coEvery { deleteTodoUseCase(any()) } returns mockk(relaxed = true)
        val viewModel = givenAViewModel()
        //when
        viewModel.dispatch(TodoListIntent.DeleteItemIntent(0L, {}))
        //then
        testScheduler.advanceUntilIdle()
        coVerify {
            deleteTodoUseCase(
                id = 0,
            )
        }
    }

    private fun givenAViewModel() = TodoViewModel(
        addTodoUseCase = addTodoUseCase,
        deleteTodoUseCase = deleteTodoUseCase,
        getTodosUseCase = getTodosUseCase,
        updateTodoUseCase = updateTodoUseCase,
        savedStateHandle = SavedStateHandle()
    )
}