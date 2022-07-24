package com.example.todoApi.todoApi.domain

import java.time.LocalDateTime

// TODO: なぜrepositoryのinterfaceをdomain層に置くか、ちゃんと言語化して説明できるようにする
interface TaskRepository {
    fun create(task: Task, createAt: LocalDateTime = LocalDateTime.now()): TaskId?
    fun findById(taskId: TaskId): Task?
}