package com.example.order_food.controllers

import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderUpdateDto
import com.example.order_food.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/order")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun create(@RequestBody dto: OrderCreateDto) = orderService.create(dto)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = orderService.getOne(id)

    @GetMapping
    fun getAll() = orderService.getAll()

    @PutMapping("update/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: OrderUpdateDto) = orderService.update(id,dto)

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = orderService.delete(id)
}