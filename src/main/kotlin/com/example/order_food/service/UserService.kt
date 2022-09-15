package com.example.order_food.service

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.Language
import com.example.order_food.enums.Step

interface UserService {
    fun getLanguage(chatId: String): Language?
    fun setLang(chatId: String, lang: Language)
    fun setStep(chatId: String, step: Step)
    fun getStep(chatId: String): Step
    fun create(userCreateDto: UserCreateDto):User
    fun update(user:User):User
    fun saveUser(telegramId: String): User
}