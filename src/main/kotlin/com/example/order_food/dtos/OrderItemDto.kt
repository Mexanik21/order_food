package com.example.order_food.dtos

import com.example.order_food.Entity.*


data class  OrderItemCreateDto(
    var orderId: Long,
    var foodId: Long,
    var count:Int

)

data class OrderItemUpdateDto(
    var orderId: Long,
    var foodId: Long,
    var count:Int
)

data class OrderItemResponseDto(
    var orderId: Long,
    var foodId: Long,
    var count:Int
) {
    companion object{
        fun toDto(o: OrderItem) = o.run {
            OrderItemResponseDto(order.id!!, food.id!!, count)
        }
    }
}