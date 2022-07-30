package com.example.todoApi.todoApi.controller

import com.example.todoApi.todoApi.domain.DomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(e: DomainException): ResponseEntity<ErrorResponse> =
        ResponseEntity(ErrorResponse(e.message), HttpStatus.BAD_REQUEST)


    @ExceptionHandler(Exception::class)
    fun handleOtherException(e: Exception): ResponseEntity<ErrorResponse> =
        ResponseEntity(ErrorResponse(e.message), HttpStatus.INTERNAL_SERVER_ERROR)
}

data class ErrorResponse(val message: String?)