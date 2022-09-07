package com.example.order_food.controllers

import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.AddressUpdateDto
import com.example.order_food.service.AddressService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("address")
class AddressController(
    private val addressService: AddressService
) {

    @PostMapping
    fun create(@RequestBody dto: AddressCreateDto) = addressService.create(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id: Long) = addressService.getOne(id)

    @GetMapping
    fun getAll() = addressService.getAll()

    @PutMapping("{id}")
    fun update(@RequestParam id: Long, @RequestBody dto: AddressUpdateDto) = addressService.update(id,dto)

    @DeleteMapping("{id}")
    fun delete(@RequestParam id: Long) = addressService.delete(id)
}