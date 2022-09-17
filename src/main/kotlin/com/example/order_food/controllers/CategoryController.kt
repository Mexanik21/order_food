package com.example.order_food.controllers

import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.CategoryUpdateDto
import com.example.order_food.service.CategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @PostMapping("create")
    fun create(@RequestBody dto: CategoryCreateDto) = categoryService.createCategory(dto)

    // get sub category
    @GetMapping("{id}")
    fun getSub(@PathVariable id:Long) = categoryService.getSub(id)

    // Get Head category
    @GetMapping()
    fun getHead() = categoryService.getHead()

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = categoryService.delete(id)

    @PutMapping("update/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: CategoryUpdateDto) = categoryService.updateCategory(id, dto)

}