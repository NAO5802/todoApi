package com.example.todoApi.todoApi.usecase

import com.example.todoApi.todoApi.domain.Task
import com.example.todoApi.todoApi.domain.TaskId
import com.example.todoApi.todoApi.domain.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskUseCase(val repository: TaskRepository) {

    @Transactional
    fun create(task: Task): Task =
        repository.create(task)
            .let { taskId -> repository.findById(taskId) }

    fun find(taskId: TaskId): Task = repository.findById(taskId)

    @Transactional
    fun delete(taskId: TaskId): TaskId =
        repository.findById(taskId)
            .let { task -> repository.delete(task.id) }

}