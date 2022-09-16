package com.example.order_food.service.impl

import com.example.order_food.Entity.File
import com.example.order_food.dtos.FileCreateDto
import com.example.order_food.dtos.FileResponseDto
import com.example.order_food.dtos.FileUpdateDto
import com.example.order_food.repository.FileRepository
import com.example.order_food.service.FileService
import org.hashids.Hashids
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream
import java.nio.file.Files
import kotlin.io.path.Path


@Service
class FileServiceImpl(
    private val fileRepository:FileRepository
): FileService {
    val folder2:String = "src\\main\\resources\\static\\"

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

    override fun getFile(fileId: Long): FileInputStream {
        val fileDb = fileRepository.findById(fileId).get()
        return FileInputStream(folder2+"/"+getFileName(fileDb.path!!))
    }


    override fun fileUpload(request: MultipartHttpServletRequest) {
        val fileName = request.fileNames
        val file: MultipartFile? = request.getFile(fileName.next())

        if(file != null){

            var fileDb = fileRepository.save(File(
                null,
                getFileExtention(file.originalFilename!!)!!,
                null
            ))

            fileDb.hashId = fileDb.id.let { Hashids().encode(it!!) }
            fileDb.path = "${getPathExtention(folder2)}${fileDb.hashId}.${getFileExtention(file.originalFilename!!)}"


            val path = Path(fileDb.path!!)
            fileRepository.save(fileDb)
            Files.copy(file.inputStream,path)
        }

    }

    fun getFileExtention(fileName: String): String? {
        val dot = fileName.lastIndexOf(".")
        if (dot > 0 && dot <= fileName.length-2){
            return fileName.substring(dot+1)
        }
        return null
    }

    fun getPathExtention(pathName:String): String? {
        val split = pathName.split("\\.")
        return split[split.size - 1]
    }

    fun getFileName(name:String):String{
        val dot = name.lastIndexOf("\\")
        return name.substring(dot+1)
    }
}