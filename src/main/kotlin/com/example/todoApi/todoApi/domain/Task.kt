package com.example.todoApi.todoApi.domain

import java.util.*

data class Task private constructor(
    val id: TaskId,
    val name: TaskName,
    val status: TaskStatus,
    val description: TaskDescription?,
    val createdBy: AdminUserName
) {
    companion object {
        fun of(
            id: TaskId,
            name: TaskName,
            status: TaskStatus,
            description: TaskDescription?,
            createdBy: AdminUserName
        ): Task =
            Task(id, name, status, description, createdBy)

        fun create(name: TaskName, status: TaskStatus, description: TaskDescription?, createdBy: AdminUserName): Task =
            Task(TaskId.new(), name, status, description, createdBy)
    }

    fun update(name: TaskName, status: TaskStatus, description: TaskDescription?): Task =
        this.copy(name = name, status = status, description = description)
}

data class Tasks(val list: List<Task>)

data class TaskId(val value: UUID) {
    companion object {
        fun new(): TaskId = TaskId(UUID.randomUUID())
        fun fromString(value: String): TaskId =
            try {
                TaskId(UUID.fromString(value))
            } catch (e: Exception) {
                throw DomainException(e.message)
            }
    }
}

data class TaskName(val value: String)

enum class TaskStatus {
    TODO, INPROGRESS, DONE;

    companion object {

        fun fromString(value: String): TaskStatus =
            try {
                valueOf(value)
            } catch (e: Exception) {
                throw DomainException(e.message)
            }
    }
}

data class TaskDescription(val value: String)
data class AdminUserName(val value: String)

enum class TaskOrderKey{
    CREATED_AT
}