package com.example.order_food.service

import com.example.order_food.dtos.*
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream

interface FileService {


    fun create(dto: FileCreateDto)
    fun getOne(id: Long): FileResponseDto
    fun getAll(): List<FileResponseDto>
    fun update(id: Long, dto: FileUpdateDto)
    fun delete(id: Long)
    fun getFile(fileId:Long,):FileInputStream
    fun fileUpload(request: MultipartHttpServletRequest)
}