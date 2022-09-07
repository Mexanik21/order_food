package com.example.order_food.controllers

import com.example.order_food.dtos.FileCreateDto
import com.example.order_food.dtos.FileUpdateDto
import com.example.order_food.service.FileService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("file")
class FileController(
    private val fileService: FileService
) {

    @PostMapping
    fun create(@RequestBody dto: FileCreateDto) = fileService.create(dto)

    @GetMapping("{id}")
    fun getOne(@RequestParam id: Long) = fileService.getOne(id)

    @GetMapping
    fun getAll() = fileService.getAll()

    @PutMapping("{id}")
    fun update(@RequestParam id: Long, @RequestBody dto: FileUpdateDto) = fileService.update(id, dto)

    @DeleteMapping("{id}")
    fun delete(@RequestParam id: Long) = fileService.delete(id)



}