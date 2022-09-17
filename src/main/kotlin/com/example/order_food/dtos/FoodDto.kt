package com.example.order_food.dtos

import com.example.order_food.Entity.Category
import com.example.order_food.Entity.File
import com.example.order_food.Entity.Food
import org.springframework.web.multipart.MultipartFile
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

data class FoodCreateDto(
    var file:MultipartFile,
    var name: String,
    var price: Long,
    var description: String?,
    var categoryId: Long,
)


data class FoodUpdateDto(
    var name: String,
    var price: Long,
    var category: Category,
    var status: Boolean,
    var fileId: Long
)

data class FoodResponseDto(
    var id:Long,
    var name: String,
    var price: Long,
    var categoryId: Long,
    var status: Boolean?,
    var fileId: Long?
){
    companion object{
        fun toDto(f:Food) = f.run {
            FoodResponseDto(id!!,name,price,category.id!!,status,file?.id)
        }
    }
}