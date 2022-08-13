package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.TaskTestDataCreator
import com.example.todoApi.todoApi.TaskTestFactory
import com.example.todoApi.todoApi.domain.*
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@Transactional // 各メソッドの最後にトランザクションがロールバックされる
internal class TaskRepositoryImplTest(
    @Autowired private val repository: TaskRepository,
    @Autowired private val driver: TaskDbDriver,
    @Autowired private val taskTestDataCreator: TaskTestDataCreator
) {

    @Test
    fun `create - 新規タスクレコードを作成し、TaskIdを返す`() {
        // given
        val task = TaskTestFactory.create()
        val createAt = LocalDateTime.of(2022, 7, 24, 8, 0)

        // when
        val actual = repository.create(task, createAt)

        // then
        val foundTaskRecord = driver.findById(actual.value)

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
        val taskId = taskTestDataCreator.create(TaskTestFactory.create(name = TaskName("財布を持ってくる")))

        // when
        val actual = repository.findById(taskId)

        // then
        assertEquals(taskId, actual.id)
        assertEquals(TaskName("財布を持ってくる"), actual.name)
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

    @Test
    fun `delete - タスクIDに該当するデータをDBから削除し、TaskIdを返す`() {
        // given
        val taskId = taskTestDataCreator.create(TaskTestFactory.create())

        // when
        val actual = repository.delete(taskId)

        // then
        val foundTaskRecord = driver.findById(taskId.value)

        assertEquals(taskId, actual, "削除したTaskIdが返ること")
        assertEquals(null, foundTaskRecord, "削除したtaskがDBに存在しないこと")

    }

    @Test
    fun `findAllWithSorted - DBに存在する全てのタスクを、作成時間降順で返す`() {
        // given
        val taskId1 = taskTestDataCreator.create(TaskTestFactory.create())
        val taskId2 = taskTestDataCreator.create(TaskTestFactory.create())
        val taskId3 = taskTestDataCreator.create(TaskTestFactory.create())

        // when
        val actual = repository.findAllWithSorted(TaskOrderKey.CREATED_AT)

        // then
        assertEquals(taskId3, actual.list[0].id)
        assertEquals(taskId2, actual.list[1].id)
        assertEquals(taskId1, actual.list[2].id)
    }

    @Test
    @Sql("/sql/delete_all_tasks.sql")
    fun `findAllWithSorted - DBにタスクが存在しない時は、空のリストを返す`() {
        val actual = repository.findAllWithSorted(TaskOrderKey.CREATED_AT)

        assertEquals(emptyList<Task>(), actual.list)

    }

    @Test
    fun `update - タスクレコードを更新し、TaskIdを返す`() {
        // given
        val createAt = LocalDateTime.of(2022, 8, 13, 14, 0, 0)
        val taskId = taskTestDataCreator.create(
            TaskTestFactory.create(
                name = TaskName("買い物"),
                status = TaskStatus.TODO,
                description = null,
                createdBy = AdminUserName("太郎")
            ),
            createAt
        )

        // when
        val updatedTask = Task.of(taskId, TaskName("スーパーで買い物"), TaskStatus.INPROGRESS, TaskDescription("卵を買う"), AdminUserName("太郎"))
        val actual = repository.update(updatedTask)

        // then
        val foundTaskRecord = driver.findById(actual.value)
        assertEquals(updatedTask.id, actual, "update()の戻り値がTaskIdであること")
        assertEquals(updatedTask.id.value, foundTaskRecord?.id, "update()で作成した内容がDBに存在すること")
        assertEquals(updatedTask.name.value, foundTaskRecord?.name, "update()で作成した内容がDBに存在すること")
        assertEquals(updatedTask.status.name, foundTaskRecord?.status?.name, "update()で作成した内容がDBに存在すること")
        assertEquals(updatedTask.description?.value, foundTaskRecord?.description, "update()で作成した内容がDBに存在すること")
        assertEquals(updatedTask.createdBy.value, foundTaskRecord?.createdBy, "update()で作成した内容がDBに存在すること")
        assertEquals(Timestamp.valueOf(createAt), foundTaskRecord?.createdAt, "update()で作成した内容がDBに存在すること")
    }

}