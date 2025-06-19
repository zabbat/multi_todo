package net.wandroid.mytodo.features.todo.list.presentation.components

import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth
import net.wandroid.mytodo.features.todo.list.domain.model.TodoData
import org.junit.Rule
import org.junit.Test

class TodoItemKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_checkbox_clicked_then_call_onItemChecked() {
        val called = mutableListOf<Pair<Long, Boolean>>()
        val todoData = TodoData(
            id = 1L,
            title = "title",
            content = "content",
            isDone = false,
        )
        composeTestRule.setContent {
            TodoItem(
                todoData = todoData,
                onItemChecked = { id, checked -> called.add(id to checked) },
                onItemDismissed = { _, _ -> },
            )
        }
        composeTestRule
            .onNode(hasTestTag("checkBox"))
            .performClick()

        Truth.assertThat(called).containsExactly(1L to true)

        composeTestRule
            .onNode(hasTestTag("checkBox"))
            .assertIsOn()
    }
}