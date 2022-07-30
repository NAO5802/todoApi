package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.TaskTestFactory
import com.example.todoApi.todoApi.domain.TaskId
import com.example.todoApi.todoApi.domain.TaskRepository
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
// TODO Transactionnalの範囲を調べる
// TODO jooqのWARNINGについて調べる
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
        assertEquals(task.id.value, foundTaskRecord?.id, "create()で作成した内容がDBに存在すること")
        assertEquals(task.name.value, foundTaskRecord?.name, "create()で作成した内容がDBに存在すること")
        assertEquals(task.status.name, foundTaskRecord?.status?.name, "create()で作成した内容がDBに存在すること")
        assertEquals(task.description?.value, foundTaskRecord?.description, "create()で作成した内容がDBに存在すること")
        assertEquals(task.createdBy.value, foundTaskRecord?.createdBy, "create()で作成した内容がDBに存在すること")
        assertEquals(Timestamp.valueOf(createAt), foundTaskRecord?.createdAt, "create()で作成した内容がDBに存在すること")
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

    @Test
    fun `findById - 該当するtaskがない場合はEntityNotFoundErrorをthrowする`() {
        // given
        val notExistTaskId = TaskId(UUID.fromString("95ec62c1-ac8a-4888-bfeb-059f0d648ef6"))

        // when
        val actual: () -> Unit = { repository.findById(notExistTaskId) }

        // then
        assertThrows<EntityNotFoundException>(actual)
    }
}