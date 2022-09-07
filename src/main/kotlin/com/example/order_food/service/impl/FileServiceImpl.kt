package com.example.order_food.service.impl

import com.example.order_food.Entity.File
import com.example.order_food.dtos.FileCreateDto
import com.example.order_food.dtos.FileResponseDto
import com.example.order_food.dtos.FileUpdateDto
import com.example.order_food.repository.FileRepository
import com.example.order_food.service.FileService
import org.springframework.stereotype.Service

@Service
class FileServiceImpl(
    private val fileRepository:FileRepository
): FileService {
    override fun create(dto: FileCreateDto) {
        dto.apply { fileRepository.save(File(hashId, mimeType, path)) }
    }

    override fun getOne(id: Long) = FileResponseDto.toDto(
        fileRepository.findById(id).orElseThrow{Exception("File not found this id = $id")}
    )

    override fun getAll() = fileRepository.findAll().map { FileResponseDto.toDto(it) }

    override fun update(id: Long, dto: FileUpdateDto) {
        val file = fileRepository.findById(id).orElseThrow{Exception("File not found this id = $id")}
        dto.apply {
            hashId.let { file.hashId = it }
            mimeType.let { file.mimeType = it}
            path.let { file.path = it }
        }
        fileRepository.save(file)
    }

    override fun delete(id: Long) {
        fileRepository.findById(id)
    }
}