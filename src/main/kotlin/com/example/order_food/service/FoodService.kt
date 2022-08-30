package com.example.order_food.service

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.FoodCreateDto

interface  FoodService {

    fun createFood(dto: FoodCreateDto)
}