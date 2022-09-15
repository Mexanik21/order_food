package com.example.order_food.repository

import com.example.order_food.Entity.Category
import com.example.order_food.dtos.GetCategoryDto
import org.springframework.data.jpa.repository.Query

interface CategoryRepository : BaseRepository<Category> {


    @Query("select c.name from category c where c.parent_id is null and c.deleted=false", nativeQuery = true)
    fun getCategory(): MutableList<String>


    @Query(
        "select c.name from category c where (select ca.id from category as ca where ca.name=:name and ca.deleted=false) = c.parent_id and c.deleted=false",
        nativeQuery = true
    )
    fun getSubCategory(name: String): MutableList<String>?


    fun existsByName(name: String):Boolean

    @Query("select c.name from category c where c.parent_id=(select c.parent_id from category  c where c.name=:name) and c.deleted=false", nativeQuery = true)
    fun getLastCategory2(name:String):MutableList<String>

    fun findByIdAndDeletedIsFalse(id: Long):Category?

    @Query("""select c.* from Category c where c.deleted = false and c.parent_id = :id""", nativeQuery = true)
    fun findByParentIdAndDeletedIsFalse(id: Long):List<Category>

    fun findAllByDeletedIsFalse():List<Category>
    @Query("""select c.* from Category c where c.deleted = false and c.parent_id IS NULL""", nativeQuery = true)
    fun findHeadByDeletedIsFalse():List<Category>



    @Query("select c.name from category c where c.id=(select ca.parent_id from category ca where ca.name=:name)", nativeQuery = true)
    fun  getBackCategory(name:String):String?
}