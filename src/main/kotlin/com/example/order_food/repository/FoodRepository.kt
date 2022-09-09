package com.example.order_food.repository

import com.example.order_food.Entity.Food
import org.springframework.data.jpa.repository.Query

interface FoodRepository :BaseRepository<Food>  {
    @Query("select f.name from food f where f.category_id=(select c.id from category  c where c.name=:name)", nativeQuery = true)
    fun getFoods(name:String):MutableList<String>



    fun findByName(name: String):Food

    @Query("select f.name from food f where f.id=:id", nativeQuery = true)
    fun nameFindById(id:Long):String

    fun existsByName(name:String):Boolean


}