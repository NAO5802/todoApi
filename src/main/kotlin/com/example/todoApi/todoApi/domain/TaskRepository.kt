package com.example.todoApi.todoApi.domain

import java.time.LocalDateTime

interface TaskRepository {
    fun create(task: Task, createAt: LocalDateTime = LocalDateTime.now()): TaskId
    fun findById(taskId: TaskId): Task
    fun delete(taskId: TaskId): TaskId
    fun findAllWithSorted(orderKey: TaskOrderKey = TaskOrderKey.CREATED_AT): Tasks
    fun update(task: Task): TaskId
}