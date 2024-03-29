package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.TaskTestDataCreator
import com.example.todoApi.todoApi.TaskTestFactory
import com.example.todoApi.todoApi.controller.task.TaskPostRequest
import com.example.todoApi.todoApi.controller.task.TaskResponse
import com.example.todoApi.todoApi.controller.task.TaskResponses
import com.example.todoApi.todoApi.controller.task.TaskUpdateRequest
import com.example.todoApi.todoApi.domain.AdminUserName
import com.example.todoApi.todoApi.domain.TaskName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TaskControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val taskTestDataCreator: TaskTestDataCreator
    ) {

    @Test
    fun `pingにリクエストするとpongの文字列が返る`(){
        val actual = restTemplate.getForEntity<String>("/ping")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo("pong")
    }

    @Nested
    inner class PostTaskTest(){

        @Test
        fun `postTask - 新規タスクを作成できること(全ての項目あり)`() {
            val request = TaskPostRequest("買い出し", "TODO", "豚肉200g", "花子")
            val actual = restTemplate.postForEntity<TaskResponse>("/tasks", request)

            assertThat(actual.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(actual.body?.id).isInstanceOf(String::class.java)
            assertThat(actual.body?.name).isEqualTo("買い出し")
            assertThat(actual.body?.status).isEqualTo("TODO")
            assertThat(actual.body?.description).isEqualTo("豚肉200g")
            assertThat(actual.body?.createdBy).isEqualTo("花子")
        }

        @Test
        fun `postTask - 新規タスクを作成できること(説明文なし)`() {
            val request = TaskPostRequest("買い出し", "TODO", null, "花子")
            val actual = restTemplate.postForEntity<TaskResponse>("/tasks", request)

            assertThat(actual.statusCode).isEqualTo(HttpStatus.CREATED)
            assertThat(actual.body?.id).isInstanceOf(String::class.java)
            assertThat(actual.body?.name).isEqualTo("買い出し")
            assertThat(actual.body?.status).isEqualTo("TODO")
            assertThat(actual.body?.description).isNull()
            assertThat(actual.body?.createdBy).isEqualTo("花子")
        }

        @Test
        fun `postTask - リクエストの値が不正な場合、400エラーを返す`() {
            val request = TaskPostRequest("", "AAAAAA", null, "")
            val actual = restTemplate.postForEntity<ErrorResponse>("/tasks", request)

            assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }
    }

    @Nested
    inner class FindTaskTest(){

        @Test
        fun `findTask - 指定したIDのタスクが返ること`() {
            // given
            val taskId = taskTestDataCreator.create(TaskTestFactory.create())

            // when
            val actual = restTemplate.getForEntity<TaskResponse>("/tasks/${taskId.value}")

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(actual.body?.id).isEqualTo(taskId.value.toString())
            assertThat(actual.body?.name).isEqualTo("買い物")
            assertThat(actual.body?.status).isEqualTo("TODO")
            assertThat(actual.body?.description).isEqualTo("ひき肉を買う")
            assertThat(actual.body?.createdBy).isEqualTo("TODO太郎")
        }

        @Test
        fun `findTask - 指定したIDの形式が不正の場合、400エラーを返す`() {
            val actual = restTemplate.getForEntity<ErrorResponse>("/tasks/1234567")

            assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }

        @Test
        fun `findTask - 指定したIDが存在しない場合、404エラーを返す`() {
            val actual = restTemplate.getForEntity<ErrorResponse>("/tasks/95ec62c1-ac8a-4888-bfeb-059f0d648ef6")

            assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        }
    }

    @Nested
    inner class DeleteTaskTest(){

        @Test
        fun `deleteTask - 指定したIDのタスクが削除できること`() {
            // given
            val taskId = taskTestDataCreator.create(TaskTestFactory.create())

            // when
            val actual = restTemplate.exchange<HttpStatus>("/tasks/${taskId.value}", HttpMethod.DELETE)

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
        }

        @Test
        fun `deleteTask - 指定したIDの形式が不正の場合、400エラーを返す`() {
            val actual = restTemplate.exchange<ErrorResponse>("/tasks/1234567", HttpMethod.DELETE)

            assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }

        @Test
        fun `deleteTask - 指定したIDが存在しない場合、404エラーを返す`() {
            val actual = restTemplate.exchange<ErrorResponse>("/tasks/95ec62c1-ac8a-4888-bfeb-059f0d648ef6", HttpMethod.DELETE)

            assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        }
    }

    @Nested
    inner class FindAllTasks(){
        @Test
        fun `全てのtaskが作成時間降順で取得できる`(){
            // given
            val taskId1 = taskTestDataCreator.create(TaskTestFactory.create(name = TaskName("１番目に作成")))
            val taskId2 = taskTestDataCreator.create(TaskTestFactory.create(name = TaskName("２番目に作成")))
            val taskId3 = taskTestDataCreator.create(TaskTestFactory.create(name = TaskName("３番目に作成")))

            // when
            val actual = restTemplate.getForEntity<TaskResponses>("/tasks")

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(actual.body?.list?.get(0)?.id).isEqualTo(taskId3.value.toString())
            assertThat(actual.body?.list?.get(1)?.id).isEqualTo(taskId2.value.toString())
            assertThat(actual.body?.list?.get(2)?.id).isEqualTo(taskId1.value.toString())
        }

        @Test
        @Sql("/sql/delete_all_tasks.sql")
        fun `taskが1件もない場合、空の配列が返る`(){
            val actual = restTemplate.getForEntity<TaskResponses>("/tasks")

            assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(actual.body?.list).isEqualTo(emptyList<TaskResponse>())
        }
    }

    @Nested
    inner class PutTaskTest(){
        @Test
        fun `updateTask - 指定したIDのタスクが更新できること`(){
            // given
            val taskId = taskTestDataCreator.create(TaskTestFactory.create(createdBy = AdminUserName("佐藤")))

            // when
            val request = TaskUpdateRequest("タスク名を更新", "INPROGRESS", "進行中です")
            val actual = restTemplate.exchange<TaskResponse>("/tasks/${taskId.value}", HttpMethod.PUT, HttpEntity(request))

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(actual.body?.id).isEqualTo(taskId.value.toString())
            assertThat(actual.body?.name).isEqualTo("タスク名を更新")
            assertThat(actual.body?.status).isEqualTo("INPROGRESS")
            assertThat(actual.body?.description).isEqualTo("進行中です")
            assertThat(actual.body?.createdBy).isEqualTo("佐藤")
        }

        @Test
        fun `updateTask - リクエストの値が不正な場合、400エラーを返す`(){
            // given
            val taskId = taskTestDataCreator.create(TaskTestFactory.create())

            // when
            val request = TaskUpdateRequest("", "AAAAA", null)
            val actual = restTemplate.exchange<ErrorResponse>("/tasks/${taskId.value}", HttpMethod.PUT, HttpEntity(request))

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }

        @Test
        fun `updateTask - 指定したIDの形式が不正の場合、400エラーを返す`(){
            // when
            val request = TaskUpdateRequest("タスク名を更新", "INPROGRESS", "進行中です")
            val actual = restTemplate.exchange<ErrorResponse>("/tasks/123456", HttpMethod.PUT, HttpEntity(request))

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        }

        @Test
        fun `updateTask - 指定したIDが存在しない場合、404エラーを返す`(){
            // when
            val request = TaskUpdateRequest("タスク名を更新", "INPROGRESS", "進行中です")
            val actual = restTemplate.exchange<ErrorResponse>("/tasks/10487624-77cd-4088-9a65-27bc0510f3c6", HttpMethod.PUT, HttpEntity(request))

            // then
            assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        }
    }
}