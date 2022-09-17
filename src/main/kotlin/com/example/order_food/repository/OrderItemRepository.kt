package com.example.order_food.repository

import com.example.order_food.Entity.Food
import com.example.order_food.Entity.Order
import com.example.order_food.Entity.OrderItem
import org.springframework.data.jpa.repository.Query

interface OrderItemRepository:BaseRepository<OrderItem> {


    @Query("select b.* from buyurtmalar b where b.order_id=(select bu.id from buyurtma bu where bu.user_id=:id and bu.status is null )", nativeQuery = true)
    fun getOrderItems(id:Long):MutableList<OrderItem>

    fun existsByOrderAndFood(order: Order, food: Food):Boolean

    @Query("select b from buyurtmalar b where b.order = ?1 and b.food = ?2")
    fun findByOrderAndFood(order: Order, food: Food):OrderItem

    @Query("select b.* from buyurtmalar b where b.deleted = false and b.order_id = :id", nativeQuery = true)
    fun findByIdAndDeletedIsFalse(id: Long):List<OrderItem>


}