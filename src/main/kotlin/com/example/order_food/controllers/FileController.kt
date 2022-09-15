package com.example.order_food.controllers

import com.example.order_food.repository.FileRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.FileService
import com.example.order_food.service.impl.FileServiceImpl
import org.springframework.core.io.FileUrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("api/v1/file")
class FileController(
    private val fileService: FileServiceImpl,
    private val fileRepository: FileRepository
) {

    @PostMapping("create")
    fun create(request:MultipartHttpServletRequest){
        fileService.fileUpload(request)
    }

//    @GetMapping("{id}")
//    fun downloadFile(@PathVariable id: Long, response: HttpServletResponse): ResponseEntity<*> {
//
//    }

    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = fileService.delete(id)

    @GetMapping("download/{id}")
    fun getFile(@PathVariable id: Long, response: HttpServletResponse){
        val fileDb = fileRepository.findById(id).get()
        fileService.getOne(id, response)
        response.setHeader("Content-Disposition","attachment; filename=\"" + fileService.getFileName(fileDb.path!!)+"\"")
        response.contentType = "image/jpg"
        FileCopyUtils.copy(fileService.getOne(id, response),response.outputStream)
    }





}