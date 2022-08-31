package com.example.order_food.repository

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.GetCategoryDto
import org.springframework.data.jpa.repository.Query

interface CategoryRepository :BaseRepository<Category>  {


    @Query("select c.name from category c where c.parent_id  is null", nativeQuery = true)
     fun getCategory():MutableList<String>


 @Query("select c.name from category c where (select ca.id from category as ca where ca.name=:name) = c.parent_id", nativeQuery = true)
 fun  getSubCategory(name:String):MutableList<String>




}