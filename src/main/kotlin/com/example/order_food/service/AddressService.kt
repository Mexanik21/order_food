package com.example.order_food.service

import com.example.order_food.dtos.*

interface AddressService {

    fun create(dto: AddressCreateDto):AddressResponseDto
    fun getOne(id: Long): com.example.order_food.Entity.Address
    fun getAll(): List<AddressResponseDto>
    fun update(id: Long, dto: AddressUpdateDto)
    fun delete(id: Long)
}