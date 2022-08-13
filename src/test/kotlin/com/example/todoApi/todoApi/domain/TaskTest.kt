package com.example.todoApi.todoApi.domain

import com.example.todoApi.todoApi.TaskTestFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TaskTest {

    @Test
    fun `update - 引数で渡された情報のみ更新したTaskを返す`() {
        // given
        val task = TaskTestFactory.create()

        // when
        val updatedTask = task.update(TaskName("更新済み"), TaskStatus.INPROGRESS, TaskDescription("更新しました"))

        // then
        // 更新されない
        assertEquals(task.id, updatedTask.id, "idは更新されないこと")
        assertEquals(task.createdBy, updatedTask.createdBy, "createdByは更新されないこと")
        // 更新される
        assertEquals(TaskName("更新済み"), updatedTask.name, "nameは更新されること")
        assertEquals(TaskStatus.INPROGRESS, updatedTask.status, "statusは更新されること")
        assertEquals(TaskDescription("更新しました"), updatedTask.description, "descriptionは更新されること")
    }
}