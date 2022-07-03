package com.example.todoApi.todoApi.domain

data class Task (
    val name: TaskName,
    val status: TaskStatus,
    val description: TaskDescription?,
    val createdBy: AdminUserName
        ){
    companion object {
        fun of(): Task = TODO()
        fun create(): Task = TODO()
    }
}

data class TaskId(val value: String)
data class TaskName(val value: String)
enum class TaskStatus {
    TODO, INPROGRESS, DONE
}
data class TaskDescription(val value: String)
data class AdminUserName(val value: String)