package com.example.todoApi.todoApi

import com.example.todoApi.todoApi.domain.*

object TaskTestFactory {
    fun create(
        id: TaskId = TaskId.new(),
        name: TaskName = TaskName("買い物"),
        status: TaskStatus = TaskStatus.TODO,
        description: TaskDescription? = TaskDescription("ひき肉を買う"),
        createdBy: AdminUserName = AdminUserName("TODO太郎")
    ): Task =
        Task.of(id, name, status, description, createdBy)
}