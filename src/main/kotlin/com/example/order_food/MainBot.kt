package com.example.order_food

import com.example.order_food.handlers.CallBackHandler
import com.example.order_food.handlers.MessageHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MainBotBot(
    private val telegramConfig: TelegramConfig,
    private val messageHandler: MessageHandler,
    private val callBackHandler: CallBackHandler
) : TelegramLongPollingBot() {
    override fun getBotUsername() = telegramConfig.botUsername
    override fun getBotToken() = telegramConfig.botToken
    override fun onUpdateReceived(update: Update) {
        if (update.hasCallbackQuery()) callBackHandler.handle(this,update.callbackQuery)
        else if (update.hasMessage()) messageHandler.handle(this,update.message)
        else println("Not found")
    }


    @Component
    @ConfigurationProperties(prefix = "telegram")
    class TelegramConfig {
        lateinit var admin: String
        lateinit var chanel: String
        lateinit var chanelLink: String
        lateinit var botUsername: String
        lateinit var botToken: String
    }


}