package com.example.order_food.dtos

import org.springframework.web.multipart.MultipartFile

data class FoodCreate(
    var file:MultipartFile,
    var name: String,
    var price: Long,
    var categoryId: Long,
) {
}