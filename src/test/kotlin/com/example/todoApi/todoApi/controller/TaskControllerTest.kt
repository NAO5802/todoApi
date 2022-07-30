package com.example.todoApi.todoApi.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TaskControllerTest(
    @Autowired val restTemplate: TestRestTemplate
    ) {

    @Test
    fun `pingにリクエストするとpongの文字列が返る`(){
        val actual = restTemplate.getForEntity<String>("/ping")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo("pong")
    }

    @Test
    fun `postTask - 新規タスクを作成できること(全ての項目あり)`() {
        val request = TaskRequest("買い出し", "TODO", "豚肉200g", "花子")
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
        val request = TaskRequest("買い出し", "TODO", null, "花子")
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
        val request = TaskRequest("", "AAAAAA", null, "")
        val actual = restTemplate.postForEntity<ErrorResponse>("/tasks", request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}