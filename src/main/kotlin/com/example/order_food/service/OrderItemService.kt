package com.example.order_food.service

import com.example.order_food.Entity.OrderItem
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.OrderItemResponseDto
import com.example.order_food.dtos.OrderItemUpdateDto
import com.example.order_food.enums.OrderStatus

interface OrderItemService {

    fun create(dto: OrderItemCreateDto)
    fun getOne(id: Long): OrderItemResponseDto
    fun getAll(): List<OrderItemResponseDto>
    fun update(id: Long, dto: OrderItemUpdateDto)
    fun delete(id: Long)
    fun getOrderItems(id:Long):MutableList<OrderItem>
}