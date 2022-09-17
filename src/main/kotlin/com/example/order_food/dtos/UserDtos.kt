package com.example.order_food.dtos

import com.example.order_food.Entity.User
import com.example.order_food.enums.Language
import com.example.order_food.enums.Role
import com.example.order_food.enums.Step


data class UserCreateDto(
    var fullName: String,
    var phoneNumber: String,
    var username: String,
    var password: String,
    var role: Role?,
)

data class UserUpdateDto(
    var role: Role? = null,
    var step: Step? = null,
    var  lang: Language? = null,
    var username: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
    var password: String? = null
)

data class UserResponseDto(
    var userId: Long,
    var username: String?,
    var fullName: String?,
    var phoneNumber: String?,
    var role: Role
) {
    companion object {
        fun toDto(u: User) = u.run {
            UserResponseDto(id!!,username,fullName,phoneNumber,role!!)
        }
    }
}

data class UserDto(
    val id:Long,
    val username: String?,
    val role: Set<Role>,
    val active:Boolean
) {
    companion object{
        fun toDto(u:User) = u.run {
            UserDto(id!!,username, setOf(role!!),isEnabled!!)
        }
    }
}