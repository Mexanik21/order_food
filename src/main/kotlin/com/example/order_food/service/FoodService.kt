package com.example.order_food.service

import com.example.order_food.Entity.Food
import com.example.order_food.dtos.*
import org.springframework.http.ResponseEntity

interface FoodService {

    fun createFood(dto: FoodCreateDto): ResponseEntity<*>
    fun getFoods(categoryName:String):MutableList<String> ?
    fun getOne(id: Long): ResponseEntity<*>
    fun getAll(): ResponseEntity<*>
    fun update(id: Long, dto: FoodUpdateDto):ResponseEntity<*>
    fun delete(id: Long):ResponseEntity<*>
   fun  findByName(name: String): Food
    fun existsByName(name:String):Boolean
    fun nameFindById(id:Long):String
}