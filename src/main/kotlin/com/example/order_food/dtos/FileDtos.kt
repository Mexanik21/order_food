package com.example.order_food.dtos

import com.example.order_food.Entity.File

data class FileCreateDto(
    var foodId:Long
)

data class FileUpdateDto(
    var hashId:String,
    var mimeType:String,
    var path:String
)

data class FileResponseDto(
    var hashId:String?,
    var mimeType:String,
    var path:String?
) {
    companion object{
        fun toDto(f: File) = f.run {
            FileResponseDto(hashId!!, mimeType, path!!)
        }
    }
}