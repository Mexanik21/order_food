package com.example.order_food.dtos

data class CategoryCreateDto(
   var name:String,
   var  parentId:Long?=null
)


data class GetCategoryDto(
   var name:String
)


