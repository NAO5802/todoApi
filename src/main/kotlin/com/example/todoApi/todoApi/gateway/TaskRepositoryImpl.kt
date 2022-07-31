package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.domain.*
import com.example.todoApi.todoApi.driver.TaskDbDriver
import com.example.todoApi.todoApi.driver.gen.enums.Status
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Repository
class TaskRepositoryImpl(val driver: TaskDbDriver) : TaskRepository {

    override fun create(task: Task, createAt: LocalDateTime): TaskId =
        task.toRecord(createAt)
            .let { driver.create(it) }
            ?.let { taskId -> TaskId(taskId) }
            ?: throw RuntimeException("failed to create new task: $task")

    override fun findById(taskId: TaskId): Task =
        driver.findById(taskId.value)
            ?.toEntity()
            ?: throw EntityNotFoundException("task could not found: $taskId")

    override fun delete(taskId: TaskId): TaskId =
        driver.delete(taskId.value)
            ?.let { taskId -> TaskId(taskId) }
            ?: throw RuntimeException("failed to delete task: $taskId")
}

data class TaskRecord(
    val id: UUID,
    val name: String,
    val status: Status,
    val description: String?,
    val createdBy: String,
    val createdAt: Timestamp
)

fun Task.toRecord(createAt: LocalDateTime): TaskRecord = TaskRecord(
    id.value,
    name.value,
    adaptToRecordStatus(status),
    description?.value,
    createdBy.value,
    Timestamp.valueOf(createAt)
)

private fun adaptToRecordStatus(status: TaskStatus): Status =
    when (status){
        TaskStatus.TODO -> Status.TODO
        TaskStatus.INPROGRESS -> Status.INPROGRESS
        TaskStatus.DONE -> Status.DONE
    }


fun TaskRecord.toEntity(): Task =
    Task.of(
        TaskId(id),
        TaskName(name),
        TaskStatus.fromString(status.name),
        description?.let { description -> TaskDescription(description) },
        AdminUserName(createdBy)
    )