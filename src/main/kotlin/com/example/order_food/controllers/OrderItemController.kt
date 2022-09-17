package com.example.order_food.controllers

import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.OrderItemUpdateDto
import com.example.order_food.service.OrderItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/orderItem")
class OrderItemController(
    private val orderItemService: OrderItemService
) {

    @PostMapping("create")
    fun create(@RequestBody dto: OrderItemCreateDto) = orderItemService.create(dto)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = orderItemService.getByOrderId(id)

    @GetMapping
    fun getAll() = orderItemService.getAll()

    @PutMapping("update/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: OrderItemUpdateDto) = orderItemService.update(id, dto)

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = orderItemService.delete(id)
}