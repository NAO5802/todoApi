package com.example.todoApi.todoApi.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TaskStatusTest {
    @Test
    fun `文字列からTaskStatusを生成する`() {
        val actual = TaskStatus.fromString("TODO")
        assertEquals(TaskStatus.TODO, actual)
    }

    @Test
    fun `不正な文字列の場合、IllegalArgumentExceptionをthrowする`() {
        val actual: () -> Unit = { TaskStatus.fromString("AAAAAAAA") }
        assertThrows<IllegalArgumentException>(actual)
    }
}