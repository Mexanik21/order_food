package com.example.order_food.repository

import com.example.order_food.Entity.Order
import org.springframework.data.jpa.repository.Query

interface OrderRepository : BaseRepository<Order> {


    @Query("select b.* from buyurtma b where b.user_id=:id", nativeQuery = true)
    fun OrderdfidnByUserId(id: Long): Order


    @Query("""select * from buyurtma where deleted = false and status != 'CREATION' and id = :id """, nativeQuery = true)
    fun findByIdAndDeletedIsFalse(id:Long):Order?
    @Query("""select * from buyurtma where deleted = false and status != 'CREATION'""", nativeQuery = true)
    fun findByAllOrders():List<Order>





}