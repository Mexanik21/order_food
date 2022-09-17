package com.example.order_food.controllers

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.Role
import com.example.order_food.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("create")
    fun create(@RequestBody dto:UserCreateDto): ResponseEntity<*> {
        dto.role = Role.ADMIN
        return ResponseEntity.status(200).body(userService.create(dto))
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = userService.getOne(id)

    @GetMapping
    fun getAll() = userService.getAll()

    @DeleteMapping("{id}")
    fun delete(id:Long) = userService.delete(id)

}