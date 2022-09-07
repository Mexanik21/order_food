package com.example.order_food.service.impl

import com.example.order_food.Entity.OrderItem
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.OrderItemResponseDto
import com.example.order_food.dtos.OrderItemUpdateDto
import com.example.order_food.repository.FoodRepository
import com.example.order_food.repository.OrderItemRepository
import com.example.order_food.repository.OrderRepository
import com.example.order_food.service.OrderItemService
import org.springframework.stereotype.Service


@Service
class OrderItemServiceImpl(
    private val orderItemRepository: OrderItemRepository,
    private val orderRepository: OrderRepository,
    private val foodRepository: FoodRepository
) : OrderItemService {
    override fun create(dto: OrderItemCreateDto) {
        dto.apply { orderItemRepository.save(OrderItem(
            orderRepository.findById(orderId).orElseThrow(),
            foodRepository.findById(foodId).orElseThrow(),
            count)) }
    }

    override fun getOne(id: Long) = OrderItemResponseDto.toDto(
        orderItemRepository.findById(id).orElseThrow { Exception("OrderItem not found this id = $id") }
    )

    override fun getAll() = orderItemRepository.findAll().map { OrderItemResponseDto.toDto(it) }

    override fun update(id: Long, dto: OrderItemUpdateDto) {
        val orderItem = orderItemRepository.findById(id).orElseThrow { Exception("OrderItem not found this id = $id") }
        dto.apply {
            orderId.let { orderItem.order = orderRepository.findById(orderId).orElseThrow() }
            foodId.let { orderItem.food = foodRepository.findById(foodId).orElseThrow() }
            count.let { orderItem.count = it }
        }
        orderItemRepository.save(orderItem)
    }

    override fun delete(id: Long) {
        orderItemRepository.deleteById(id)
    }
}