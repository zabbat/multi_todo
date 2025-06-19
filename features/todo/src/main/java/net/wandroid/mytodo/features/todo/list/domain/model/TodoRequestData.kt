package net.wandroid.mytodo.features.todo.list.domain.model

import net.wandroid.mytodo.features.common.Unchanged

internal data class TodoRequestData(
    val id: Long,
    val title: String? = Unchanged,
    val content: String? = Unchanged,
    val isDone: Boolean? = Unchanged,
)