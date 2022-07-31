package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.controller.task.TaskRequest
import com.example.todoApi.todoApi.controller.task.TaskResponse
import com.example.todoApi.todoApi.controller.task.toResponse
import com.example.todoApi.todoApi.domain.*
import com.example.todoApi.todoApi.usecase.TaskUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TaskController(private val useCase: TaskUseCase) {

    @GetMapping("/ping")
    fun getPing(): String = "pong"

    @PostMapping("/tasks")
    fun postTask(@RequestBody taskRequest: TaskRequest): ResponseEntity<TaskResponse> =
        Task.create(
            TaskName(taskRequest.name),
            TaskStatus.fromString(taskRequest.status),
            taskRequest.description?.let { TaskDescription(it) },
            AdminUserName(taskRequest.createdBy)
        )
            .let { useCase.create(it) }
            .let { ResponseEntity(it.toResponse(), HttpStatus.CREATED) }

    @GetMapping("/tasks/{id}")
    fun findTask(@PathVariable id: String): ResponseEntity<TaskResponse> =
        TaskId.fromString(id)
            .let { useCase.find(it) }
            .let { ResponseEntity(it.toResponse(), HttpStatus.OK) }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<Unit> =
        TaskId.fromString(id)
            .let{ useCase.delete(it) }
            .let { ResponseEntity(HttpStatus.NO_CONTENT) }

}