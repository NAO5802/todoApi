package com.example.todoApi.todoApi.usecase

import com.example.todoApi.todoApi.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskUseCase(val repository: TaskRepository) {

    @Transactional
    fun create(task: Task): Task =
        repository.create(task)
            .let { taskId -> repository.findById(taskId) }

    fun find(taskId: TaskId): Task = repository.findById(taskId)

    fun findAllWithSorted(): Tasks = repository.findAllWithSorted()

    @Transactional
    fun delete(taskId: TaskId): TaskId =
        repository.findById(taskId)
            .let { task -> repository.delete(task.id) }

    @Transactional
    fun update(id: String, name: String, status: String, description: String?): Task =
        TaskId.fromString(id)
            .let { taskId ->  repository.findById(taskId) }
            .let { task -> task.update(TaskName(name), TaskStatus.fromString(status), description?.let { TaskDescription(description) }) }
            .let { updatedTask -> repository.update(updatedTask) }
            .let { taskId -> repository.findById(taskId) }
}