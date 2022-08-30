package com.example.order_food.controllers

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.service.CategoryService
import com.example.order_food.service.FoodService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("food")
class FoodController(

    private val foodService: FoodService
) {

    @PostMapping("create")
    fun create(@RequestBody dto: FoodCreateDto) = foodService.createFood(dto)


}