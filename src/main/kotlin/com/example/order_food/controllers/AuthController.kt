package com.example.order_food.controllers

import com.example.order_food.dtos.LoginUser
import com.example.order_food.service.impl.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("login")
    fun login(@RequestBody loginUser: LoginUser): ResponseEntity<*>? {
        return authService.login(loginUser)
    }
}