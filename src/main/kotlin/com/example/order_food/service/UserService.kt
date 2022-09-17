package com.example.order_food.service

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.Language
import com.example.order_food.enums.Step
import com.example.order_food.response.ResponseObj
import org.springframework.http.ResponseEntity

interface UserService {
    fun create(dto: UserCreateDto):ResponseEntity<*>
    fun update(user:User):User
    fun saveUser(telegramId: String): User
    fun getOne(id:Long):ResponseEntity<*>
    fun getAll():ResponseEntity<*>
    fun delete(id: Long):ResponseEntity<*>

}