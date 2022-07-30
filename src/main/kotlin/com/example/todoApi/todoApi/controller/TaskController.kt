package com.example.todoApi.todoApi.controller

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
    @ResponseBody
    fun postTask(@RequestBody taskRequest: TaskRequest): ResponseEntity<TaskResponse> =
            Task.create(
                TaskName(taskRequest.name),
                TaskStatus.fromString(taskRequest.status),
                taskRequest.description?.let{ TaskDescription(it) },
                AdminUserName(taskRequest.createdBy)
            )
                .let { useCase.create(it) }
                .let { ResponseEntity(it.toResponse(), HttpStatus.OK) }
    }

// TODO 適切な場所に置く
data class TaskRequest(
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String
)

class TaskResponse(
    val id: String,
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String
)

fun Task.toResponse(): TaskResponse = TODO()