package com.example.order_food.service

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.CategoryCreateDto

import com.example.order_food.dtos.GetCategoryDto

interface CategoryService {

    fun createCategory(dto:CategoryCreateDto):Long
    fun getCategory():MutableList<String>
    fun getSubCategory(name:String):MutableList<String>?
    fun getLastCategory(name: String):MutableList<String>
    fun getCategoryEmptyParentId(name: String): Category?

}