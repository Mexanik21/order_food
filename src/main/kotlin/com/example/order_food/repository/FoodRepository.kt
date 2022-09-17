package com.example.order_food.repository

import com.example.order_food.Entity.Food
import org.springframework.data.jpa.repository.Query

interface FoodRepository :BaseRepository<Food>  {
    @Query("select f.name from food f where f.category_id=(select c.id from category  c where c.name=:name) and f.deleted=false", nativeQuery = true)
    fun getFoods(name:String):MutableList<String>?


    fun findByName(name: String):Food

    @Query("select f.name from food f where f.id=:id", nativeQuery = true)
    fun nameFindById(id:Long):String

    fun existsByName(name:String):Boolean

    @Query("select f.name from food f where f.category_id=(select f.category_id from food  f where f.name=:name) and f.deleted=false", nativeQuery = true)
    fun getLastFoods(name:String):MutableList<String>

     @Query("select c.name from category c where c.id=(select f.category_id from food  f where f.name=:name)", nativeQuery = true)
    fun  getBackFood(name:String):String

    @Query("select f.* from Food f where f.deleted = false", nativeQuery = true)
    fun findByIdAndDeletedIsFalseFoodList():List<Food>
    @Query("select f from Food f where f.id = ?1 and f.deleted = false")
    fun findByIdAndDeletedIsFalse(id: Long):Food?

    @Query("select f from Food f where f.deleted = false")
    fun findAllByDeletedIsFalse():List<Food>

    @Query("""select f.* from Food as f where f.deleted = false and f.category_id = :id""", nativeQuery = true)
    fun getByCategoryId(id: Long):List<Food>


}