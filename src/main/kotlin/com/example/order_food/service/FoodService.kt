package com.example.order_food.service

import com.example.order_food.dtos.*

interface FoodService {

    fun createFood(dto: FoodCreateDto)
    fun getFoods(categoryName:String):MutableList<String>
    fun getOne(id: Long): FoodResponseDto
    fun getAll(): List<FoodResponseDto>
    fun update(id: Long, dto: FoodUpdateDto)
    fun delete(id: Long)
}