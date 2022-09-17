package com.example.order_food.service

import com.example.order_food.Entity.Order
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderResponseDto
import com.example.order_food.dtos.OrderUpdateDto
import org.springframework.http.ResponseEntity

interface OrderService {

    fun create(dto: OrderCreateDto)
    fun getOne(id: Long): ResponseEntity<*>
    fun getAll(): ResponseEntity<*>
    fun update(id: Long, dto: OrderUpdateDto):ResponseEntity<*>
    fun delete(id: Long):ResponseEntity<*>
    fun orderfindByUserId(id: Long): Order
}