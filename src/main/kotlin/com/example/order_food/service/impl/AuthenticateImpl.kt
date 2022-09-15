package com.example.order_food.service.impl

import com.example.order_food.getTelegramId
import com.example.order_food.repository.UserRepository
import com.example.order_food.service.Authenticate
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*

@Service
class AuthenticateImpl(private val userRepository: UserRepository) : Authenticate {
    override fun authenticate(update: Update) {
        try {
            val user = userRepository.findByTelegramId(update.getTelegramId().toString())
            println(user.lang!!.code)
            LocaleContextHolder.setLocale(Locale(user.lang!!.code))
        } catch (e: Exception) {
            if (update.channelPost == null && update.editedChannelPost == null)
                println("Bot exception: $update. Message: $e.message")
        }
    }
}