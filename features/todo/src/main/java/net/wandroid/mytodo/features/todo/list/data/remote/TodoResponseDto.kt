package net.wandroid.mytodo.features.todo.list.data.remote

internal data class TodoResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val isDone: Boolean,
)
