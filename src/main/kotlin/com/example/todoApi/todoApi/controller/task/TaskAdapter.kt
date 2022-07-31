package com.example.todoApi.todoApi.controller.task

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.domain.Tasks

data class TaskRequest(
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String
)

data class TaskResponse(
    val id: String,
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String
)

// TODO: FCCのinterface作る
data class TaskResponses(val list: List<TaskResponse>)

fun Task.toResponse(): TaskResponse = TaskResponse(
    id.value.toString(),
    name.value,
    status.name,
    description?.value,
    createdBy.value
)

fun Tasks.toResponse(): TaskResponses = TODO()