package com.example.order_food.service.impl

import com.example.order_food.Entity.Category
import com.example.order_food.Entity.Food
import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.dtos.FoodResponseDto
import com.example.order_food.dtos.FoodUpdateDto
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.repository.FileRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.service.CategoryService
import com.example.order_food.service.FoodService
import org.springframework.stereotype.Service


@Service
class FoodServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
    private val fileRepository: FileRepository
) : FoodService {


    override fun createFood(dto: FoodCreateDto) {
        val category = categoryRepository.findById(dto.categoryId)
            .orElseThrow { Exception("food not found by id: ${dto.categoryId}") }
        foodRepository.save(Food(dto.name, dto.price, category))
    }
    override fun getFoods(categoryName: String): MutableList<String> {
        return foodRepository.getFoods(categoryName)
    }

    override fun getOne(id: Long) = FoodResponseDto.toDto(
        foodRepository.findById(id).orElseThrow { Exception("Food not found $id") }
    )

    override fun getAll() = foodRepository.findAll().map { FoodResponseDto.toDto(it) }

    override fun update(id: Long, dto: FoodUpdateDto) {
        var food = foodRepository.findById(id).orElseThrow {
            Exception("Food not found $id")

        }
        dto.apply {
            name.let { food.name = it }
            price.let { food.price = it }
            category.let { food.category = it }
            status.let { food.status  = it }
            fileId.let { food.file = fileRepository.findById(fileId).orElseThrow{Exception()} }
        }
        foodRepository.save(food)
    }

    override fun delete(id: Long) {
        foodRepository.deleteById(id)
    }

    override fun findByName(name: String)=foodRepository.findByName(name)
    override fun existsByName(name: String)=foodRepository.existsByName(name)
    override fun nameFindById(id: Long)=foodRepository.nameFindById(id)


}