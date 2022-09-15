package com.example.order_food.service.impl

import com.example.order_food.Entity.File
import com.example.order_food.repository.FileRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.service.FileService
import org.hashids.Hashids
import org.springframework.core.io.FileUrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse
import kotlin.io.path.Path


@Service
class FileServiceImpl(
    private val fileRepository:FileRepository,
    private val foodRepository: FoodRepository

): FileService {

    private val folder:String = "C:\\Users\\Abdul\\IdeaProjects\\orderBot\\src\\main\\resources\\static\\"
    val folder2:String = "src\\main\\resources\\static\\"


    override fun getOne(id: Long, response: HttpServletResponse):FileInputStream {
        val fileDb = fileRepository.findById(id).get()
        return FileInputStream(folder2+"/"+getFileName(fileDb.path!!))

    }



    override fun delete(id: Long) {
        fileRepository.findById(id)
    }


    override fun fileUpload(request: MultipartHttpServletRequest) {
        val fileName = request.fileNames
        val file:MultipartFile? = request.getFile(fileName.next())

        if(file != null){

            var fileDb = fileRepository.save(File(
                null,
                getFileExtention(file.originalFilename!!)!!,
                null
            ))

            fileDb.hashId = fileDb.id?.let { Hashids().encode(it) }
            fileDb.path = "${getPathExtention(folder2)}${fileDb.hashId}.${getFileExtention(file.originalFilename!!)}"


            val path = Path(fileDb.path!!)
            fileDb = fileRepository.save(fileDb)
            Files.copy(file.inputStream,path)
        }

    }

    override fun getFile(id: Long): FileUrlResource {
        val file = fileRepository.findById(id)
        val o = FileUrlResource(file.get().path!!)
        return o
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

    public fun getFileName(name:String):String{
        val dot = name.lastIndexOf("\\")
        return name.substring(dot+1)
    }



}