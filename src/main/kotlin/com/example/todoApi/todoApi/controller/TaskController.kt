package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.domain.TaskId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController {

    @GetMapping("/ping")
    fun getPing(): String = "pong"

    @PostMapping("/tasks")
    fun postTask(): TaskId = TODO()

}