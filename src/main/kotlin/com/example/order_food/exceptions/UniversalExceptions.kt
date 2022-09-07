package com.example.order_food.exceptions

import org.springframework.http.HttpStatus

data class UniversalExceptions(
    var msg: String? = null,
    val status: HttpStatus? = null
): RuntimeException() {
}