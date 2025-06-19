package net.wandroid.mytodo.features.todo.list.di

import androidx.lifecycle.SavedStateHandle
import com.squareup.moshi.Moshi
import io.mockk.mockk
import net.wandroid.mytodo.features.auth.AuthInterceptorProvider
import net.wandroid.mytodo.features.config.ConfigProvider
import net.wandroid.mytodo.features.todo.list.data.remote.TodoApi
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import net.wandroid.mytodo.features.todo.list.domain.user_cases.AddTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.DeleteTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.GetTodosUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.UpdateTodoUseCase
import net.wandroid.mytodo.features.todo.list.presentation.TodoViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertNotNull

class TodoModuleTest : KoinTest {

    private val mockSupportModule = module {
        single<AuthInterceptorProvider> {
            val mockInterceptor = mockk<Interceptor>(relaxed = true)
            object : AuthInterceptorProvider {
                override fun getAuthInterceptor(): Interceptor = mockInterceptor
            }
        }

        single<ConfigProvider> {
            object : ConfigProvider {
                override val baseUrl: String = "https://baseUrl.test"
            }
        }

        single { SavedStateHandle() }
    }


    @Before
    fun setUp() {
        startKoin {
            modules(
                todoModule,
                mockSupportModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `todoModule should provide all dependencies`() {
        val viewModel: TodoViewModel = getKoin().get()
        assertNotNull(viewModel)

        val getTodosUseCase: GetTodosUseCase = getKoin().get()
        assertNotNull(getTodosUseCase)
        val updateTodoUseCase: UpdateTodoUseCase = getKoin().get()
        assertNotNull(updateTodoUseCase)
        val deleteTodoUseCase: DeleteTodoUseCase = getKoin().get()
        assertNotNull(deleteTodoUseCase)
        val addTodoUseCase: AddTodoUseCase = getKoin().get()
        assertNotNull(addTodoUseCase)

        val moshi: Moshi = getKoin().get()
        assertNotNull(moshi)
        val todoApi: TodoApi = getKoin().get()
        assertNotNull(todoApi)

        val todoRepository: TodoRepository = getKoin().get()
        assertNotNull(todoRepository)

        val client: OkHttpClient = getKoin().get()
        assertNotNull(client)


    }
}
