package com.example.order_food.controllers

import com.example.order_food.dtos.FoodCreateDto
import com.example.order_food.dtos.FoodUpdateDto
import com.example.order_food.service.FoodService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/food")
class FoodController(

    private val foodService: FoodService
) {

    @PostMapping("create")
    fun create(@RequestBody dto: FoodCreateDto) = foodService.createFood(dto)

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long) = foodService.getOne(id)

    @GetMapping()
    fun getAll() = foodService.getAll()

    @PutMapping("update/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto:FoodUpdateDto) = foodService.update(id, dto)

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = foodService.delete(id)




}