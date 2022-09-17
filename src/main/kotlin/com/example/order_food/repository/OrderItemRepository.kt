package com.example.order_food.repository

import com.example.order_food.Entity.Food
import com.example.order_food.Entity.Order
import com.example.order_food.Entity.OrderItem
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface OrderItemRepository:BaseRepository<OrderItem> {


    @Query("select b.* from buyurtmalar b where b.order_id=(select bu.id from buyurtma bu where bu.user_id=:id and bu.status is null )", nativeQuery = true)
    fun getOrderItems(id:Long):MutableList<OrderItem>

    fun existsByOrderAndFood(order: Order, food: Food):Boolean


    fun findByOrderAndFood(order: Order, food: Food):OrderItem

    @Query("select b.* from buyurtmalar b where b.deleted = false and b.order_id = :id", nativeQuery = true)
    fun findByIdAndDeletedIsFalse(id: Long):List<OrderItem>

    @Modifying
    @Transactional
    @Query("delete from buyurtmalar b where b.id = ?1")
    override fun deleteById(id: Long)

    @Query("update buyurtmalar set count = count+1 where id=:id returning *", nativeQuery = true)
    fun addCountOrderItem(id:Long):OrderItem

    @Query("update buyurtmalar set count = count-1 where id=:id returning *", nativeQuery = true)
    fun reduceCountOrderItem(id:Long):OrderItem


}