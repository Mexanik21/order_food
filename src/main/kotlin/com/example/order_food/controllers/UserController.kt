package com.example.order_food.controllers

import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun create(@RequestBody dto:UserCreateDto) = userService.create(dto)
}