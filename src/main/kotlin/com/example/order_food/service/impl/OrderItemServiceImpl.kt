package com.example.order_food.service.impl

import com.example.order_food.Entity.OrderItem
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.OrderItemResponseDto
import com.example.order_food.dtos.OrderItemUpdateDto
import com.example.order_food.dtos.Response
import com.example.order_food.enums.OrderStatus
import com.example.order_food.repository.FoodRepository
import com.example.order_food.repository.OrderItemRepository
import com.example.order_food.repository.OrderRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.OrderItemService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class OrderItemServiceImpl(
    private val orderItemRepository: OrderItemRepository,
    private val orderRepository: OrderRepository,
    private val foodRepository: FoodRepository
) : OrderItemService {
    override fun create(dto: OrderItemCreateDto) {
       val order= orderRepository.findById(dto.orderId).orElseThrow{Exception()}
       val food=  foodRepository.findById(dto.foodId).orElseThrow{Exception()}

        if(orderItemRepository.existsByOrderAndFood(order,food)){

            val orderItem=orderItemRepository.findByOrderAndFood(order,food)

            orderItem.count+=dto.count

            orderItemRepository.save(orderItem)


        }else{
            orderItemRepository.save(OrderItem(
                order,
                food,
                dto.count
            ))
        }

    }

    override fun getByOrderId(id: Long):ResponseEntity<*> {
        val orderItems = orderItemRepository.findByIdAndDeletedIsFalse(id)
        return if (orderItems.isNotEmpty()){
            ResponseEntity.status(200).body(ResponseObj("Success", 200, true, orderItems.map { OrderItemResponseDto.toDto(it) }))
        } else {
            ResponseEntity.status(404).body(ResponseObj("Order item not found $id", 404, false, null))
        }
    }



    override fun getAll() = orderItemRepository.findAll().map { OrderItemResponseDto.toDto(it) }

    override fun update(id: Long, dto: OrderItemUpdateDto) {
        val orderItem = orderItemRepository.findById(id).orElseThrow { Exception("OrderItem not found this id = $id") }
        dto.apply {
            orderId.let { orderItem.order = orderRepository.findById(orderId).orElseThrow{Exception()} }
            foodId.let { orderItem.food = foodRepository.findById(foodId).orElseThrow{Exception()} }
            count.let { orderItem.count = it }
        }
        orderItemRepository.save(orderItem)
    }

    override fun delete(id: Long) {
        orderItemRepository.deleteById(id)
    }

    override fun getOrderItems(id: Long)=orderItemRepository.getOrderItems(id)
}