package com.example.todoApi.todoApi.domain

import java.time.LocalDateTime

interface TaskRepository {
    fun create(task: Task, createAt: LocalDateTime = LocalDateTime.now()): TaskId
    fun findById(taskId: TaskId): Task
}