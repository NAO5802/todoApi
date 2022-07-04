package com.example.todoApi.todoApi.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TaskControllerTest(
    @Autowired val restTemplate: TestRestTemplate
    ) {

    @Test
    fun `pingにリクエストするとpongの文字列が返る`(){
        val response = restTemplate.getForEntity<String>("/ping")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("pong")
    }

    @Test
    fun `postTask - 新規タスクを作成できること`() {

    }
}