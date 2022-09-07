package com.example.order_food.service.impl

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.CategoryCreateDto

import com.example.order_food.dtos.GetCategoryDto
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.service.CategoryService
import org.springframework.stereotype.Service


@Service
class CategoryServiceImpl (
   private val categoryRepository: CategoryRepository
        ):CategoryService{


    override fun createCategory(dto:CategoryCreateDto):Long {

        return if(dto.parentId==null){
            val category=Category(dto.name)
            categoryRepository.save(category)
            category.id!!


        }else{
            val category=categoryRepository.findById(dto.parentId!!).orElseThrow{Exception("category not found by id: $dto.parentId")}
            val supCategory=Category(dto.name,category)
            categoryRepository.save(supCategory)
            return  supCategory.id!!
        }

    }

    override fun getCategory(): MutableList<String> {

        return categoryRepository.getCategory()

    }

    override fun getSubCategory(name:String): MutableList<String>? {
        return categoryRepository.getSubCategory(name)
    }

    override fun getLastCategory(name: String): MutableList<String>? {
        return  categoryRepository.getLastCategory(name)
    }


}