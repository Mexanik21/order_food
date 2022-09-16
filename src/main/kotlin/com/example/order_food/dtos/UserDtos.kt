package com.example.order_food.dtos

import com.example.order_food.Entity.User
import com.example.order_food.enums.Language
import com.example.order_food.enums.Role
import com.example.order_food.enums.Step


data class UserCreateDto(
    var telegramId: String,
    var role: Role? = Role.USER,
    var step: Step? = Step.START,
    var  lang: Language = Language.UZ,
    var password:String? = null,
    var username: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
)

data class UserUpdateDto(
    var role: Role? = null,
    var step: Step? = null,
    var  lang: Language? = null,
    var username: String? = null,
    var fullName: String? = null,
    var phoneNumber: String? = null,
)

data class UserResponseDto(
    var role: Role,
    var step: Step,
    var  lang: Language,
    var username: String?,
    var fullName: String,
    var phoneNumber: String,
) {
    companion object{
        fun toDto(u: User) = u.run {
            UserResponseDto(role!!,step!!,lang,username,fullName!!,phoneNumber!!)
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