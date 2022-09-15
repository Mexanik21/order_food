package com.example.order_food.service

import com.example.order_food.Entity.File
import org.springframework.core.io.FileUrlResource
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse

interface FileService {


    fun getOne(id: Long, response: HttpServletResponse): FileInputStream

    fun delete(id: Long)
    fun fileUpload(request: MultipartHttpServletRequest)

    fun getFile(id: Long): FileUrlResource

}