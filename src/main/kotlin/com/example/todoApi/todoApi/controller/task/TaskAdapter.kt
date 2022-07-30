package com.example.todoApi.todoApi.controller.task

import com.example.todoApi.todoApi.domain.Task

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

fun Task.toResponse(): TaskResponse = TaskResponse(
    id.value.toString(),
    name.value,
    status.name,
    description?.value,
    createdBy.value
)