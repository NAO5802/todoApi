package com.example.todoApi.todoApi.gateway

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.domain.TaskRepository
import com.example.todoApi.todoApi.driver.TaskDbDriver
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class TaskRepositoryImpl(val driver: TaskDbDriver): TaskRepository {

    override fun create(task: Task): Task =
        TaskRecord.from(task)
            .let{ driver.create(it) }
            .let{ Task.of()}
}

// TODO: Task起点でtoRecordするのと、Record起点でfromTaskするのどっちがいいんだろう
data class TaskRecord(
    val id: UUID,
    val name: String,
    val status: String,
    val description: String?,
    val createdBy: String,
    val createdAt: LocalDateTime
){
    companion object {
        fun from(task: Task): TaskRecord = TODO()
    }
}