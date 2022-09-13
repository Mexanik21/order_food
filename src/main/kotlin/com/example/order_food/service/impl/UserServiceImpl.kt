package com.example.order_food.service.impl

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.Language
import com.example.order_food.enums.Step
import com.example.order_food.repository.UserRepository
import com.example.order_food.security.MyPasswordEncoder
import com.example.order_food.service.UserService


import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val myPasswordEncoder: MyPasswordEncoder
) : UserService {





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

    override fun create(userCreateDto: UserCreateDto): User {
        val telegramId=userCreateDto.telegramId
        if (userRepository.existsByTelegramId(telegramId)) {
            return userRepository.findByTelegramId(telegramId)
        } else {
            userCreateDto.let {
                return userRepository.save(
                    User(it.telegramId)

                )
            }
        }

    }

    override fun update(user: User): User {
        val u = userRepository.findById(user.id!!).orElseThrow { Exception("") }

        user.lang.let { u.lang = it }
        user.cache.let { u.cache = it }
        user.phoneNumber.let { u.phoneNumber = it }
        user.fullName.let { u.fullName = it }
        user.step.let { u.step = it }
        user.modifiedDate.let { u.modifiedDate=it }


        return userRepository.save(user)
    }




    override fun setStep(chatId: String, step: Step) {
        val user = userRepository.findByTelegramId(chatId)
        user.step = step
        userRepository.save(user)
    }





}
