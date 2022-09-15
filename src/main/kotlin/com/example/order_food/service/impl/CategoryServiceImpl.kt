package com.example.order_food.service.impl

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.CategoryCreateDto
import com.example.order_food.dtos.CategoryResponseDto
import com.example.order_food.dtos.CategoryUpdateDto

import com.example.order_food.repository.CategoryRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class CategoryServiceImpl (
   private val categoryRepository: CategoryRepository
        ):CategoryService{

    override fun updateCategory(id:Long ,dto: CategoryUpdateDto): ResponseEntity<*> {

        var c = categoryRepository.findByIdAndDeletedIsFalse(id)
        return if (c != null){
            dto.name.let { c!!.name = it }
            c = categoryRepository.save(c)
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true,
                CategoryResponseDto(c.id!!,c.name,c.category!!.id!!)
            ))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Category not found $id", 404, false, null
            ))
        }

    }

    override fun getHead(): ResponseEntity<*> {
        val categories = categoryRepository.findHeadByDeletedIsFalse()
        return if(categories.isEmpty()){
            ResponseEntity.status(404).body(ResponseObj("not found categories", 404, false, null))
        } else {
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true,categories.map { CategoryResponseDto.toDto(it) }))
        }
    }

    override fun getSub(id: Long): ResponseEntity<*> {
        val categories = categoryRepository.findByParentIdAndDeletedIsFalse(id)
        return if (categories.isNotEmpty()){
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true,categories.map { CategoryResponseDto.toDto(it) }))
        } else{
            ResponseEntity.status(404).body(ResponseObj("Category not found $id", 404, false, null))
        }
    }

    override fun delete(id: Long): ResponseEntity<*> {
        val category = categoryRepository.findByIdAndDeletedIsFalse(id)
        return if (category != null){
            category.deleted = true
            categoryRepository.save(category)
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, null))
        } else{
            ResponseEntity.status(404).body(ResponseObj("Category not found", 404, false, null))
        }
    }


    override fun createCategory(dto:CategoryCreateDto): ResponseEntity<*> {

         if(dto.parentId == null){
             var category=Category(dto.name)
             category = categoryRepository.save(category)
             return ResponseEntity.status(200).body(ResponseObj(
                 "Success",
                 200,
                 true,
                 category
             ))
        } else {
            if( categoryRepository.findByIdAndDeletedIsFalse(dto.parentId!!) != null){
                var category=Category(dto.name,categoryRepository.findByIdAndDeletedIsFalse(dto.parentId!!))
                category = categoryRepository.save(category)
                return ResponseEntity.status(200).body(ResponseObj(
                    "Success",
                    200,
                    true,
                    category
                ))
            } else {
                return ResponseEntity.status(404).body(ResponseObj(
                    "Parent category not found ${dto.parentId}",
                    404,
                    false,
                    null
                ))
            }

         }
    }

    override fun getCategory(): MutableList<String> {

        return categoryRepository.getCategory()

    }

    override fun getSubCategory(name:String): MutableList<String>? {

        return if(name=="sss"){
            categoryRepository.getCategory()
        }else{
            categoryRepository.getSubCategory(name)
        }

    }




}