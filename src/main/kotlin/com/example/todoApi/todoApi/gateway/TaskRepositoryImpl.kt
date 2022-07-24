package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.domain.*
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TaskRepositoryImpl(val driver: TaskDbDriver) : TaskRepository {

    override fun create(task: Task, createAt: LocalDateTime): TaskId =
        task.toRecord(createAt)
            .let { driver.create(it) }
            .let { TaskId(it) }

    override fun findById(taskId: TaskId): Task =
        driver.findById(taskId.value).toEntity()
}

data class TaskRecord(
    val id: UUID,
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String,
    val createdAt: LocalDateTime
)

fun Task.toRecord(createAt: LocalDateTime): TaskRecord = TaskRecord(
    this.id.value,
    this.name.value,
    this.status.name,
    this.description?.value,
    this.createdBy.value,
    createAt
)

fun TaskRecord.toEntity(): Task =
    Task.of(
        TaskId(id),
        TaskName(name),
        TaskStatus.fromString(status),
        description?.let { description -> TaskDescription(description) },
        AdminUserName(createdBy)
    )