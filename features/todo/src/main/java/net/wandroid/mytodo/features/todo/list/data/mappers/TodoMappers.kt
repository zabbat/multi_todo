package net.wandroid.mytodo.features.todo.list.data.mappers

import net.wandroid.mytodo.features.todo.list.data.remote.TodoPatchRequestDto
import net.wandroid.mytodo.features.todo.list.data.remote.TodoResponseDto
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import net.wandroid.mytodo.features.todo.list.domain.model.TodoRequestData

internal fun TodoResponseDto.toTodoData(): TodoData = TodoData(
    id = this.id,
    title = this.title,
    content = this.content,
    isDone = this.isDone,
)


internal fun TodoRequestData.toTodoPatchRequestDto(): TodoPatchRequestDto =
    TodoPatchRequestDto(
        id = this.id,
        title = this.title,
        content = this.content,
        isDone = this.isDone,
    )