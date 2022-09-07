package com.example.order_food.controllers

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.dtos.FoodUpdateDto
import com.example.order_food.service.CategoryService
import com.example.order_food.service.FoodService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("food")
class FoodController(

    private val foodService: FoodService
) {

    @PostMapping("create")
    fun create(@RequestBody dto: FoodCreateDto) = foodService.createFood(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id:Long) = foodService.getOne(id)

    @GetMapping()
    fun getAll() = foodService.getAll()

    @PutMapping("{id}")
    fun update(@RequestParam id: Long, @RequestBody dto:FoodUpdateDto) = foodService.update(id, dto)

    @DeleteMapping("{id}")
    fun delete(@RequestParam id: Long) = foodService.delete(id)


}