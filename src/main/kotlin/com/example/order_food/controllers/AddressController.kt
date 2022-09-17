package com.example.order_food.controllers

import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.AddressUpdateDto
import com.example.order_food.service.AddressService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/address")
class AddressController(
    private val addressService: AddressService
) {

    @PostMapping("create")
    fun create(@RequestBody dto: AddressCreateDto) = addressService.create(dto)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = addressService.getOne(id)

    @GetMapping
    fun getAll() = addressService.getAll()

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: AddressUpdateDto) = addressService.update(id,dto)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = addressService.delete(id)
}