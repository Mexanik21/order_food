package com.example.order_food.service

import com.example.order_food.dtos.*

interface FileService {


    fun create(dto: FileCreateDto)
    fun getOne(id: Long): FileResponseDto
    fun getAll(): List<FileResponseDto>
    fun update(id: Long, dto: FileUpdateDto)
    fun delete(id: Long)
}