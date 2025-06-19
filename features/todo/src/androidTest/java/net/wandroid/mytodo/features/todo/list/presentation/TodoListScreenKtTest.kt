package net.wandroid.mytodo.features.todo.list.presentation

//import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule


@OptIn(ExperimentalCoroutinesApi::class)
class TodoListScreenKtTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: TodoViewModel = mockk(relaxed = true)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<TodoViewModel> {
                    viewModel
                }
            }
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun call_TodoViewModel_dispatch_with_InitializeIntent_on_first_composition() = runTest {
        every { viewModel.state } returns MutableStateFlow(TodoListState.Init)
        composeTestRule.setContent {
            TodoListScreen()
        }
        testScheduler.advanceUntilIdle()

        verify {
            viewModel.dispatch(TodoListIntent.InitializeIntent)
        }
    }
}