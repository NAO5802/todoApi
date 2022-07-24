package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.TaskTestFactory
import com.example.todoApi.todoApi.domain.TaskRepository
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
internal class TaskRepositoryImplTest(
    @Autowired private val repository: TaskRepository,
    @Autowired private val driver: TaskDbDriver
) {

    @Test
    fun `create - 新規タスクレコードを作成し、TaskIdを返す`() {
        // given
        val task = TaskTestFactory.create()
        val createAt = LocalDateTime.of(2022, 7, 24, 8,0)

        // when
        val actual = repository.create(task, createAt)
        val foundTaskRecord = driver.findById(actual.value)

        // then
        assertEquals(task.id, actual, "create()の戻り値がTaskIdであること")
        assertEquals(task.id, foundTaskRecord.id, "create()で作成した内容がDBに存在すること")
        assertEquals(task.name, foundTaskRecord.name, "create()で作成した内容がDBに存在すること")
        assertEquals(task.status, foundTaskRecord.status, "create()で作成した内容がDBに存在すること")
        assertEquals(task.description, foundTaskRecord.description, "create()で作成した内容がDBに存在すること")
        assertEquals(task.createdBy, foundTaskRecord.createdBy, "create()で作成した内容がDBに存在すること")
        assertEquals(createAt, foundTaskRecord.createdAt, "create()で作成した内容がDBに存在すること")
    }

    @Test
    fun `findById - タスクIDに該当するTaskエンティティを返す`() {
        // given
        val task = TaskTestFactory.create()
        repository.create(task)

        // when
        val actual = repository.findById(task.id)

        // then
        assertEquals(task.id, actual.id)
        assertEquals(task.name, actual.name)
    }
}