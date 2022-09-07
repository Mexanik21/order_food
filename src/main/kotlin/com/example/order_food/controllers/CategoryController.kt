package com.example.order_food.controllers

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.service.CategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("category")
class CategoryController(
    private val categoryService: CategoryService

) {

    @PostMapping("create")
    fun create(@RequestBody dto: CategoryCreateDto) = categoryService.createCategory(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id:Long) = categoryService.getCategory()

    @GetMapping()
    fun getAll() = categoryService.getCategory()
}