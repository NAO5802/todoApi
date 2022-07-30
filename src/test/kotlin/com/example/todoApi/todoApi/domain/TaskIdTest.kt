package com.example.todoApi.todoApi.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class TaskIdTest {
    @Test
    fun `fromString - UUID形式のStringからTaskIdを生成する`() {
        val actual = TaskId.fromString("2b060726-b5ca-4a63-a96e-7730d0d88527")

        assertEquals(TaskId(UUID.fromString("2b060726-b5ca-4a63-a96e-7730d0d88527")), actual)
    }

    @Test
    fun `fromString - 引数のStringの形式が不正な場合、DomainExceptionを返す`() {
        val actual: () -> Unit = { TaskId.fromString("1234567") }

        assertThrows<DomainException>(actual)
    }
}
