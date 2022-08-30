package com.example.order_food.controllers

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.service.CategoryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("category")
class CategoryController(
    private val categoryService: CategoryService

) {

    @PostMapping("create")
    fun create(@RequestBody dto: CategoryCreateDto) = categoryService.createCategory(dto)

}