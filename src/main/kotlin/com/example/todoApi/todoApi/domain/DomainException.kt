package com.example.todoApi.todoApi.domain

class DomainException(
    override val message: String?,
): RuntimeException() {}