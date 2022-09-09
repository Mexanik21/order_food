package com.example.order_food.service

import com.example.order_food.Entity.Order
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderResponseDto
import com.example.order_food.dtos.OrderUpdateDto

interface OrderService {

    fun create(dto: OrderCreateDto)
    fun getOne(id: Long): OrderResponseDto
    fun getAll(): List<OrderResponseDto>
    fun update(id: Long, dto: OrderUpdateDto)
    fun delete(id: Long)
    fun OrderdfidnByUserId(id: Long): Order
}