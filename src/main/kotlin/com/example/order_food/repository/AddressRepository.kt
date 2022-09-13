package com.example.order_food.repository

import com.example.order_food.Entity.Address

interface AddressRepository:BaseRepository<Address> {

    fun findByUserId(userId: Long):Address
}