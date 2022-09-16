package com.example.order_food.repository

import com.example.order_food.Entity.Address
import org.springframework.data.jpa.repository.Query

interface AddressRepository:BaseRepository<Address> {


    @Query("select a.* from address a where a.user_id=:id order by  a.created_date desc limit 1", nativeQuery = true)
    fun lastAfindByUserId(id: Long):Address
}