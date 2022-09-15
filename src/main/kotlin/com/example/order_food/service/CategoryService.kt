package com.example.order_food.service

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.CategoryUpdateDto

import org.springframework.http.ResponseEntity

interface CategoryService {

    fun createCategory(dto:CategoryCreateDto):ResponseEntity<*>
    fun getCategory():MutableList<String>
    fun getSubCategory(name:String):MutableList<String>?


    fun updateCategory(id:Long, dto: CategoryUpdateDto):ResponseEntity<*>
    fun getHead():ResponseEntity<*>

    fun getSub(id: Long):ResponseEntity<*>

    fun delete(id: Long): ResponseEntity<*>

}