package net.wandroid.mytodo.features.todo.list.data.remote

import net.wandroid.mytodo.features.common.Unchanged

internal data class TodoPatchRequestDto(
    val id: Long,
    val title: String? = Unchanged,
    val content: String? = Unchanged,
    val isDone: Boolean? = Unchanged,
)