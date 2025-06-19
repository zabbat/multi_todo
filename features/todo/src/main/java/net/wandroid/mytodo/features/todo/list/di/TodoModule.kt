package net.wandroid.mytodo.features.todo.list.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.wandroid.mytodo.features.auth.AuthInterceptorProvider
import net.wandroid.mytodo.features.config.ConfigProvider
import net.wandroid.mytodo.features.todo.list.data.remote.TodoApi
import net.wandroid.mytodo.features.todo.list.data.repositories.TodoRepositoryImpl
import net.wandroid.mytodo.features.todo.list.domain.model.repositories.TodoRepository
import net.wandroid.mytodo.features.todo.list.domain.user_cases.AddTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.DeleteTodoUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.GetAllTodosUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.GetTodosUseCase
import net.wandroid.mytodo.features.todo.list.domain.user_cases.UpdateTodoUseCase
import net.wandroid.mytodo.features.todo.list.presentation.TodoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val todoModule = module {
    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptorProvider>().getAuthInterceptor())
            .addInterceptor(logging)
            .build()
    }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<TodoApi> {
        Retrofit.Builder()
            .baseUrl(get<ConfigProvider>().baseUrl)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(TodoApi::class.java)
    }

    single<TodoRepository> {
        TodoRepositoryImpl(get())
    }

    factoryOf(::GetAllTodosUseCase)
    factoryOf(::GetTodosUseCase)
    factoryOf(::UpdateTodoUseCase)
    factoryOf(::AddTodoUseCase)
    factoryOf(::DeleteTodoUseCase)
    viewModelOf(::TodoViewModel)
}