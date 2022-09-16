package com.example.order_food.repository

import com.example.order_food.Entity.User
import org.springframework.data.jpa.repository.Query

interface UserRepository : BaseRepository<User> {

    @Query("select u from users u where u.telegramId = ?1")
    fun findByTelegramId(telegramId: String):User

    @Query("select c from users c where c.telegramId = ?1 and c.deleted = false")
    fun findByTelegramIdAndDeletedFalse(telegramId: String): User?

    fun existsByTelegramId(telegramId: String):Boolean

    fun findByUsername(username:String):User?



}