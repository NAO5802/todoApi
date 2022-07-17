package com.example.todoApi.todoApi.usecase

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.domain.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// TODO: ServiceとComponentの違いを理解する
@Service
class TaskUseCase(val repository: TaskRepository) {

    @Transactional
    fun create(task: Task): Task = repository.create(task)
}