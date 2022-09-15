package com.example.order_food.service.impl

import com.example.order_food.Entity.Order
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderResponseDto
import com.example.order_food.dtos.OrderUpdateDto
import com.example.order_food.repository.OrderRepository
import com.example.order_food.repository.UserRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
): OrderService {
    override fun create(dto: OrderCreateDto) {
        dto.apply {
            orderRepository.save(Order(
                userRepository.findById(userId).orElseThrow{Exception()},
                phoneNumber))
        }
    }

    override fun getOne(id: Long):ResponseEntity<*> {
        val order = orderRepository.findByIdAndDeletedIsFalse(id)
        return if(order != null) {
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, OrderResponseDto.toDto(order)))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Order not found $id", 404, false, null))

        }
    }

    override fun getAll():ResponseEntity<*> {
        val orders = orderRepository.findByAllOrders()
        return if (orders.isNotEmpty()) {
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, orders.map { OrderResponseDto.toDto(it) }))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Orders is empty", 404, false,  null))
        }
    }


    override fun update(id: Long, dto: OrderUpdateDto):ResponseEntity<*> {
        var order = orderRepository.findByIdAndDeletedIsFalse(id)

        if (order != null){
            dto.apply {
                address.let { order!!.address = it }
                phoneNumber.let { order!!.phoneNumber = it }
            }
            order = orderRepository.save(order)
           return ResponseEntity.status(200).body(ResponseObj("Success", 200, true, OrderResponseDto.toDto(order)))
        } else {
            return ResponseEntity.status(404).body(ResponseObj("Order not found $id", 404, false, null))
        }

    }

    override fun delete(id: Long):ResponseEntity<*> {
        val order = orderRepository.findByIdAndDeletedIsFalse(id)
        return if (order != null) {
            order.deleted = true
            orderRepository.save(order)
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, null))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Order not found $id", 404, false,  null))
        }
    }

    override fun OrderdfidnByUserId(id: Long)=orderRepository.OrderdfidnByUserId(id)


}