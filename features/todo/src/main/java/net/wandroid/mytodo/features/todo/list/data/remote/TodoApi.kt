package net.wandroid.mytodo.features.todo.list.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface TodoApi {
    @GET("api/todo/list")
    suspend fun getTodos(
    ): List<TodoResponseDto>?

    @GET("api/todo/listAll")
    suspend fun getAllTodos(): List<TodoResponseDto>?

    @POST("api/todo/update")
    suspend fun updateTodo(@Body todoPatchRequestDto: TodoPatchRequestDto): TodoResponseDto?

    @POST("api/todo/add")
    suspend fun addTodo(@Body todoAddRequestDto: TodoAddRequestDto): TodoResponseDto?

    @GET("api/todo/delete")
    suspend fun deleteTodo(
        @Query("id") id: Long,
    ): Long
}


