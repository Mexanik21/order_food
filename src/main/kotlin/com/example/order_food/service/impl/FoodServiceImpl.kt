package com.example.order_food.service.impl

import com.example.order_food.Entity.Food
import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.dtos.FoodResponseDto
import com.example.order_food.dtos.FoodUpdateDto
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.repository.FileRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.FoodService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class FoodServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
    private val fileRepository: FileRepository
) : FoodService {


    override fun createFood(dto: FoodCreateDto): ResponseEntity<*> {
        val category = categoryRepository.findByIdAndDeletedIsFalse(dto.categoryId)
        if (category != null){
            val food = foodRepository.save(Food(dto.name, dto.price, dto.description!!, category,null))
            return ResponseEntity.status(200).body(ResponseObj<Any>(
                "Success",
                200,
                true,
                food
            ))
        } else {
            return ResponseEntity.status(404).body(ResponseObj<Any>(
                "Category not found",
                404,
                false,
                null
            ))
        }

    }
    override fun getFoods(categoryName: String): MutableList<String>? {
        return foodRepository.getFoods(categoryName)
    }

    override fun getOne(id: Long):ResponseEntity<*> {

        val food = foodRepository.findByIdAndDeletedIsFalse(id)
        return if (food != null){
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, food))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Food not found $id", 404, false, null))
        }


    }

    override fun getAll():ResponseEntity<*> {
        val foods = foodRepository.findAllByDeletedIsFalse()
        return if (foods.isNotEmpty()){
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, foods.map{ FoodResponseDto.toDto(it) }))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Foods empty", 404, false, null))
        }
    }

    override fun update(id: Long, dto: FoodUpdateDto):ResponseEntity<*> {
        var food = foodRepository.findByIdAndDeletedIsFalse(id)
        if (food != null){
            dto.apply {
                name.let { food.name = it }
                price.let { food.price = it }
                category.let { food.category = it }
                status.let { food.status  = it }
                fileId.let { food.file = fileRepository.findById(fileId).orElseThrow{Exception()} }
            }
            return ResponseEntity.status(200).body(ResponseObj("Success", 200, true, FoodResponseDto.toDto(foodRepository.save(food))))
        } else {
            return ResponseEntity.status(404).body(ResponseObj("Food not found $id", 404, false, null))
        }

    }

    override fun delete(id: Long):ResponseEntity<*> {
        val food = foodRepository.findByIdAndDeletedIsFalse(id)
         if (food != null){
            food.deleted = true
            foodRepository.save(food)
            return ResponseEntity.status(200).body(ResponseObj("Success", 200, true, null))
        } else {
            return ResponseEntity.status(404).body(ResponseObj("Food not found $id", 404, false, null))
        }

    }

    override fun findByName(name: String)=foodRepository.findByName(name)
    override fun existsByName(name: String)=foodRepository.existsByName(name)
    override fun nameFindById(id: Long)=foodRepository.nameFindById(id)


}