package com.example.order_food.dtos

import com.example.order_food.Entity.Category
import com.example.order_food.Entity.File

data class FoodCreateDto(
    var name: String,
    var price: Long,
    var categoryId: Long

)