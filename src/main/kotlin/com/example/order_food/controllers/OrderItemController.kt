package com.example.order_food.controllers

import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.OrderItemUpdateDto
import com.example.order_food.service.OrderItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("orderItem")
class OrderItemController(
    private val orderItemService: OrderItemService
) {

    @PostMapping
    fun create(@RequestBody dto: OrderItemCreateDto) = orderItemService.create(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id: Long) = orderItemService.getOne(id)

    @GetMapping
    fun getAll() = orderItemService.getAll()

    @PutMapping("{id}")
    fun update(@RequestParam id: Long, @RequestBody dto: OrderItemUpdateDto) = orderItemService.update(id, dto)

    @DeleteMapping("{id}")
    fun delete(@RequestParam id: Long) = orderItemService.delete(id)
}