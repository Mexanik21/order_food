package com.example.order_food.controllers

import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderUpdateDto
import com.example.order_food.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("order")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping
    fun create(@RequestBody dto: OrderCreateDto) = orderService.create(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id: Long) = orderService.getOne(id)

    @GetMapping
    fun getAll() = orderService.getAll()

    @PutMapping("{id}")
    fun update(@RequestParam id: Long, @RequestBody dto: OrderUpdateDto) = orderService.update(id,dto)

    @DeleteMapping("{id}")
    fun delete(@RequestParam id: Long) = orderService.delete(id)
}