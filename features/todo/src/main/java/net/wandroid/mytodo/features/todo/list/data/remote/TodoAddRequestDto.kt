package net.wandroid.mytodo.features.todo.list.data.remote

internal data class TodoAddRequestDto(
    val title: String,
    val content: String,
    val isDone: Boolean = false,
)