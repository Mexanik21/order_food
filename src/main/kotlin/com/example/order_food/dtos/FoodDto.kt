package com.example.order_food.dtos

import com.example.order_food.Entity.Category
import com.example.order_food.Entity.File
import com.example.order_food.Entity.Food
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

data class FoodCreateDto(
    var name: String,
    var price: Long,
    var categoryId: Long,
    var fileId: Long
)


data class FoodUpdateDto(
    var name: String,
    var price: Long,
    var category: Category,
    var status: Boolean,
    var fileId: Long
)

data class FoodResponseDto(
    var name: String,
    var price: Long,
    var categoryId: Long,
    var status: Boolean?,
    var fileId: Long
){
    companion object{
        fun toDto(f:Food) = f.run {
            FoodResponseDto(name,price,category.id!!,status,file!!.id!!)
        }
    }
}