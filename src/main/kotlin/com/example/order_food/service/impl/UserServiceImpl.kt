package com.example.order_food.service.impl

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.dtos.UserResponseDto
import com.example.order_food.enums.Language
import com.example.order_food.enums.Role
import com.example.order_food.enums.Step
import com.example.order_food.repository.UserRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.security.MyPasswordEncoder
import com.example.order_food.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val myPasswordEncoder: MyPasswordEncoder
) : UserService {

    override fun saveUser(telegramId: String): User {
        if (userRepository.existsByTelegramId(telegramId)) {
            return userRepository.findByTelegramId(telegramId)
        } else {
            userRepository.save(User(telegramId))
        }

        return userRepository.findByTelegramId(telegramId)

    }


    override fun getLanguage(chatId: String): Language? {
        val user = userRepository.findByTelegramId(chatId)
        return user.lang
    }

    override fun setLang(chatId: String, lang: Language) {
        val user = userRepository.findByTelegramId(chatId)
        user.lang = lang
        userRepository.save(user)
    }

    override fun getStep(chatId: String): Step {
        val user = userRepository.findByTelegramId(chatId)
        return user.step!!

    }

    override fun create(dto: UserCreateDto): ResponseEntity<*> {
        val responseObj: ResponseObj<Any> = ResponseObj("null", 200, false)
        if (userRepository.existsByUsername(dto.username)) {
            responseObj.message = "Username already exists"
            responseObj.httpStatus = 409
            responseObj.success = false
            responseObj.obj = null
            return ResponseEntity.status(200).body(responseObj)
        } else {
            if(dto.password.isEmpty()){
                responseObj.apply {
                    message = "Password is empty"
                    httpStatus = 409
                    success = false
                    obj = null
                }
                return  ResponseEntity.status(409).body(responseObj)
            }
            return ResponseEntity.status(200).body(
                ResponseObj(
                    "success",
                    200,
                    true,
                    UserResponseDto.toDto( userRepository.save( User( System.currentTimeMillis().toString(), Role.ADMIN,null,null, dto.username, dto.fullName, dto.phoneNumber, null, myPasswordEncoder.passwordEncoder()!!.encode(dto.password))))
                )
            )
        }

    }

    override fun update(user: User): User {
        val u = userRepository.findById(user.id!!).orElseThrow { Exception("") }
        user.lang.let { u.lang = it }
        user.cache.let { u.cache = it }
        user.username.let { u.username = it }
        user.fullName.let { u.fullName = it }
        user.password.let { u.password = it }
        return userRepository.save(user)
    }

    override fun getOne(id: Long): ResponseEntity<*> {
        val user = userRepository.findByIdAndDeletedIsFalse(id)
        if(user != null)  {
             return ResponseEntity.status(200).body(ResponseObj("success", 200, true, UserResponseDto.toDto(user)))
        }
        return ResponseEntity.status(404).body(ResponseObj("User Not found", 404, false, null))
    }

    override fun getAll(): ResponseEntity<*> {
        val list = userRepository.findAllByDeletedIsFalse()
        val responseList: ArrayList<UserResponseDto> = ArrayList()
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObj("users not", 404, false, null))
        }else {
            list.map {
                responseList.add(
                    UserResponseDto.toDto(it)
                )
            }
            return ResponseEntity.status(200).body(ResponseObj("Success", 200, true, responseList))
        }

    }

    override fun delete(id:Long): ResponseEntity<*> {
        val user = userRepository.findByIdAndDeletedIsFalse(id)
        if (user != null){
            user.deleted = true
            userRepository.save(user)
            return ResponseEntity.status(200).body(ResponseObj("user is deleted", 200, true, null))
        }
        return ResponseEntity.status(404).body(ResponseObj("User not found", 404, false, null))
    }


    override fun setStep(chatId: String, step: Step) {
        val user = userRepository.findByTelegramId(chatId)
        user.step = step
        userRepository.save(user)
    }


}
