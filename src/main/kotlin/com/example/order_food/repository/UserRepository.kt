package com.example.order_food.repository

import com.example.order_food.Entity.User
import org.springframework.data.jpa.repository.Query

interface UserRepository : BaseRepository<User> {

    @Query("select u from users u where u.telegramId = ?1")
    fun findByTelegramId(telegramId: String):User


    fun existsByTelegramId(telegramId: String):Boolean

}