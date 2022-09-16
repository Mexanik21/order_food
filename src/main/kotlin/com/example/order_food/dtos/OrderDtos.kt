package com.example.order_food.dtos

import com.example.order_food.Entity.Address
import com.example.order_food.Entity.Order
import com.example.order_food.Entity.User
import com.example.order_food.enums.OrderStatus


data class OrderCreateDto(
    var userId: Long,
    var phoneNumber: String
)

data class OrderUpdateDto(
    var address: Address?,
    var status: OrderStatus?
)

data class OrderResponseDto(
    var userId: Long,
    var phoneNumber: String,

) {
    companion object{
        fun toDto(o: Order) = o.run {
            OrderResponseDto(user.id!!,o.phoneNumber)
        }
    }
}