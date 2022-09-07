package com.example.order_food.dtos

import com.example.order_food.Entity.Address
import com.example.order_food.Entity.User

data class AddressCreateDto(
    var userId: Long,
    var address: String,
    var comment:String,
    var latitide:String,
    var longitude:String
)

data class AddressUpdateDto(
    var address: String,
    var comment:String,
    var latitide:String,
    var longitude:String
)

data class AddressResponseDto(
    var userId: Long,
    var address: String,
    var comment:String,
    var latitide:String,
    var longitude:String
){
    companion object{
        fun toDto(a:Address) = a.run {
            AddressResponseDto(user.id!!, address, comment, latitide, longitude)
        }
    }
}