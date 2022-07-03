package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.usecase.TaskUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(private val useCase: TaskUseCase) {

    @GetMapping("/ping")
    fun getPing(): String = "pong"

    @PostMapping("/tasks")
    fun postTask(@RequestBody taskRequest: TaskRequest): TaskResponse =
        // TODO リクエストを受け取ってドメイン作成
        Task.create()
        .let{ useCase.create(it) }
            .toResponse()
}

// TODO 適切な場所に置く
data class TaskRequest(
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String
)

// TODO
class TaskResponse()

// TODO: Task起点でtoResponseするのと、Response起点でfromTaskするのどっちがいいんだろう
fun Task.toResponse(): TaskResponse= TODO()