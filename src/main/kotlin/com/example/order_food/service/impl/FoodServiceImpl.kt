package com.example.order_food.service.impl

import com.example.order_food.Entity.Category
import com.example.order_food.Entity.Food
import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.service.CategoryService
import com.example.order_food.service.FoodService
import org.springframework.stereotype.Service


@Service
class FoodServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository
) : FoodService {


    override fun createFood(dto: FoodCreateDto) {
        val category = categoryRepository.findById(dto.categoryId)
            .orElseThrow { Exception("food not found by id: ${dto.categoryId}") }
        foodRepository.save(Food(dto.name, dto.price, category))

    }


}