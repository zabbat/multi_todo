package net.wandroid.mytodo.features.todo.list.domain.model

internal data class TodoData(
    val id: Long,
    val title: String,
    val content: String,
    val isDone: Boolean,
)