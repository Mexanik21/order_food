package com.example.order_food.repository

import com.example.order_food.Entity.Address
import org.springframework.data.jpa.repository.Query

interface AddressRepository:BaseRepository<Address> {

    @Query("select a from Address a where a.user.id = ?1")
    fun findByUserId(userId: Long):Address
}