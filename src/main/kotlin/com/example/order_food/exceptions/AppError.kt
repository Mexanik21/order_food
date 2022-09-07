package com.example.order_food.exceptions

import java.sql.Timestamp
import java.time.LocalDateTime

data class AppError(
    var timestamp: Timestamp? = Timestamp.valueOf(LocalDateTime.now()),
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null,
    val path: String? = null,
)