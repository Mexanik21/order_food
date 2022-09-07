package com.example.order_food.exceptions.handler

import com.example.order_food.exceptions.UniversalExceptions
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class UniversalExceptionHandler(): ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [UniversalExceptions::class])
    fun exceptionHandler(e: UniversalExceptions, request: WebRequest?): ResponseEntity<*> {
        return ResponseEntity.status(e.status!!).body(e.msg)
    }
}