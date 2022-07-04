package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.domain.*
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TaskRepositoryImpl(val driver: TaskDbDriver) : TaskRepository {

    override fun create(task: Task): Task =
        task.toRecord()
            .let { driver.create(it) }
            .let {
                Task.of(
                    TaskId(it.id),
                    TaskName(it.name),
                    TaskStatus.fromString(it.status),
                    it.description?.let { description -> TaskDescription(description) },
                    AdminUserName(it.createdBy)
                )
            }
}

data class TaskRecord(
    val id: UUID,
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String,
    val createdAt: LocalDateTime
)

// TODO: Task起点でtoRecordするのと、Record起点でfromTaskするのどっちがいいんだろう
fun Task.toRecord(): TaskRecord = TODO()