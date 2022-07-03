package com.example.todoApi.todoApi.domain

// TODO: なぜrepositoryのinterfaceをdomain層に置くか、ちゃんと言語化して説明できるようにする
interface TaskRepository {
    fun create(task: Task): Task
}