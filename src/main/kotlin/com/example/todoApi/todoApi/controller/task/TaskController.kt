package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.controller.task.*
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
    fun postTask(@RequestBody taskPostRequest: TaskPostRequest): ResponseEntity<TaskResponse> =
        useCase.create(taskPostRequest.name, taskPostRequest.status, taskPostRequest.description, taskPostRequest.createdBy)
            .let { ResponseEntity(it.toResponse(), HttpStatus.CREATED) }

    @GetMapping("/tasks/{id}")
    fun findTask(@PathVariable id: String): ResponseEntity<TaskResponse> =
        useCase.find(id)
            .let { ResponseEntity(it.toResponse(), HttpStatus.OK) }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<Unit> =
         useCase.delete(id)
            .let { ResponseEntity(HttpStatus.NO_CONTENT) }

    @GetMapping("/tasks")
    fun findAllTasks(): ResponseEntity<TaskResponses> =
        useCase.findAllWithSorted()
            .let { ResponseEntity(it.toResponse(), HttpStatus.OK) }

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: String, @RequestBody request: TaskUpdateRequest): ResponseEntity<TaskResponse> =
        useCase.update(id, request.name, request.status, request.description)
            .let { ResponseEntity(it.toResponse(), HttpStatus.OK) }

}