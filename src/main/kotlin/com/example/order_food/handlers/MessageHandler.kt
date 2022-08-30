package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.ConstRU
import com.example.order_food.ConstUZ
import com.example.order_food.enums.Language
import com.example.order_food.enums.Step
import com.example.order_food.service.impl.CategoryServiceImpl
import com.example.order_food.service.impl.UserServiceImpl

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import java.security.PrivateKey


@Service
class MessageHandler(
    private val userServiceImpl: UserServiceImpl,
    private val categoryServiceImpl: CategoryServiceImpl

) {

    fun handle(sender: AbsSender, message: Message) {
        val text = message.text
        val telegramUser = message.from
        val chatId = telegramUser.id.toString()
        val user = userServiceImpl.saveUser(chatId)
        val step = user.step
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = chatId

        if (message.hasText()) {

            when (text) {
                "/start" -> {
                    when (step) {

                        Step.START -> {
                            sendMessage.text = ConstUZ.CHOOSE_LANGUAGE_MESSAGE
                            sendMessage.replyMarkup = InlineKeyboardButtons().LanguageInlineKeyboard()
                            sender.execute(sendMessage)
                        }
                    }
                }
                "1234" -> {
                    if (userServiceImpl.getLanguage(chatId) == Language.UZ) {
                        if (userServiceImpl.getStep(chatId) == Step.MENU) {
                            sendMessage.text = ConstUZ.MENU_MESSAGE
                            sendMessage.replyMarkup = ReplyKeyboardButtons().MenuKeyboard(
                                ConstUZ.ORDER_BUTTON, ConstUZ.ABOUT_US_BUTTON,
                                ConstUZ.SETTINGS_BUTTON, ConstUZ.LOCATION_BUTTON
                            )
                            sender.execute(sendMessage)


                        }

                    } else if (userServiceImpl.getLanguage(chatId) == Language.RU) {
                        if (userServiceImpl.getStep(chatId) == Step.MENU) {

                            sendMessage.text = ConstRU.MENU_MESSAGE
                            sendMessage.replyMarkup = ReplyKeyboardButtons().MenuKeyboard(
                                ConstRU.ORDER_BUTTON, ConstRU.ABOUT_US_BUTTON,
                                ConstRU.SETTINGS_BUTTON, ConstRU.LOCATION_BUTTON
                            )
                            sender.execute(sendMessage)
                        }
                    }


                }

                ConstUZ.ORDER_BUTTON->{
                    sendMessage.text=ConstUZ.ORDER_BUTTON
                    sendMessage.replyMarkup=ReplyKeyboardButtons().CategoryKeyboard(categoryServiceImpl)
                    sender.execute(sendMessage)
                }



            }












        } else if (message.hasContact()) {
            if (userServiceImpl.getLanguage(chatId) == Language.UZ && userServiceImpl.getStep(chatId) == Step.LANG) {
                sendMessage.text = ConstUZ.SMS_CODE_MESSAGE
                sendMessage.replyMarkup = ReplyKeyboardButtons().backKeyboard(ConstUZ.BACK_BUTTON)
                sender.execute(sendMessage)
                userServiceImpl.setStep(chatId, Step.MENU)
            } else if (userServiceImpl.getLanguage(chatId) == Language.RU && userServiceImpl.getStep(chatId) == Step.LANG) {
                sendMessage.text = ConstRU.SMS_CODE_MESSAGE
                sendMessage.replyMarkup = ReplyKeyboardButtons().backKeyboard(ConstRU.BACK_BUTTON)
                sender.execute(sendMessage)
                userServiceImpl.setStep(chatId, Step.MENU)
            }


        }

    }


}





