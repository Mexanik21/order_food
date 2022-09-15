package com.example.order_food.dtos

import com.example.order_food.Entity.Category

data class CategoryCreateDto(
   var name:String,
   var  parentId:Long?=null
)


data class GetCategoryDto(
   var name:String
)
data class CategoryUpdateDto(
   var name: String
)

data class CategoryResponseDto(
   var id:Long,
   var name: String,
   var parentId:Long?
) {
   companion object{
      fun toDto(c: Category) = c.run{
         CategoryResponseDto(id!!,name, category?.id)
      }
   }
}


