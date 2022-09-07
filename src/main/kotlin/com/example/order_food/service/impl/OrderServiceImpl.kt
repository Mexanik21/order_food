package com.example.order_food.service.impl

import com.example.order_food.Entity.Order
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderResponseDto
import com.example.order_food.dtos.OrderUpdateDto
import com.example.order_food.repository.OrderRepository
import com.example.order_food.repository.UserRepository
import com.example.order_food.service.OrderService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
): OrderService {
    override fun create(dto: OrderCreateDto) {
        dto.apply {
            orderRepository.save(Order(
                userRepository.findById(userId).orElseThrow(),
                address,
                phoneNumber,
                status))
        }
    }

    override fun getOne(id: Long) = OrderResponseDto.toDto(
        orderRepository.findById(id).orElseThrow{Exception("Order not found this id = $id")}
    )

    override fun getAll() = orderRepository.findAll().map { OrderResponseDto.toDto(it) }

    override fun update(id: Long, dto: OrderUpdateDto) {
        val order = orderRepository.findById(id).orElseThrow{Exception("Order not found this id = $id")}

        dto.apply {

           userId.let { order.user = userRepository.findById(userId).orElseThrow() }
           address.let { order.address = it }
           phoneNumber.let { order.phoneNumber = it }
        }
        orderRepository.save(order)
    }

    override fun delete(id: Long) {
        orderRepository.deleteById(id)
    }


    
}