package com.example.todoApi.todoApi.usecase

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.domain.TaskRepository
import org.springframework.stereotype.Service

// TODO: ServiceとComponentの違いを理解する
@Service
class TaskUseCase(val repository: TaskRepository) {

    fun create(task: Task): Task = repository.create(task)
}