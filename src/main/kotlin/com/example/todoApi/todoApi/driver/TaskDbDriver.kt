package com.example.todoApi.todoApi.driver

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.gateway.TaskRecord
import org.springframework.stereotype.Component

@Component
class TaskDbDriver {
    fun create(taskRecord: TaskRecord): TaskRecord = TODO()
}