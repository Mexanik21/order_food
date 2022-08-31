package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.enums.Step
import com.example.order_food.enums.Step.*
import com.example.order_food.service.MessageSourceService
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
    private val messageSourceService: MessageSourceService,
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

                        START -> {
                            sendMessage.text = messageSourceService.getMessage(CHOOSE_LANGUAGE_MESSAGE)
                            sendMessage.replyMarkup = InlineKeyboardButtons.LanguageInlineKeyboard()
                            sender.execute(sendMessage)
                            userServiceImpl.setStep(chatId, LANG)

                        }

                        INPUT_CONTACT -> {
                            sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                            sendMessage.replyMarkup = ReplyKeyboardButtons.MenuKeyboard(
                                messageSourceService.getMessage(ORDER_BUTTON),
                                messageSourceService.getMessage(ABOUT_US_BUTTON),
                                messageSourceService.getMessage(SETTINGS_BUTTON),
                                messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                            )
                            sender.execute(sendMessage)

                            userServiceImpl.setStep(chatId, MENU)

                        }


                    }

                }

                else -> {
                    when (step) {

                        MENU -> {
                            if (text == messageSourceService.getMessage(ORDER_BUTTON)) {
                                sendMessage.text = text
                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(categoryServiceImpl.getCategory(),messageSourceService)
                                sender.execute(sendMessage)
                            }else{
                                sendMessage.text = text
                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(categoryServiceImpl.getSubCategory(text),messageSourceService)
                                sender.execute(sendMessage)
                            }



                        }


                        //
//                   if (userServiceImpl.getLanguage(chatId) == Language.UZ) {
//                       if (text == ConstUZ.BACK_BUTTON) {
//
//                           if (userServiceImpl.getStep(chatId).toString() == ConstUZ.ORDER_BUTTON) {
//                               sendMessage.text = ConstUZ.ORDER_BUTTON
//                               sendMessage.replyMarkup =
//                                   ReplyKeyboardButtons().CategoryKeyboard(categoryServiceImpl.getCategory())
//                               sender.execute(sendMessage)
//                           } else {
//                               sendMessage.text = userServiceImpl.getStep(chatId).toString()
//                               sendMessage.replyMarkup = ReplyKeyboardButtons().CategoryKeyboard(
//                                   categoryServiceImpl.getSubCategory(userServiceImpl.getStep(chatId).toString())
//                               )
//                               sender.execute(sendMessage)
//                           }
//
//
//                       }
//
//                       if (text == ConstUZ.ORDER_BUTTON) {
//                           userServiceImpl.setStep(chatId, Step.valueOf(text))
//                           sendMessage.text = ConstUZ.ORDER_BUTTON
//                           sendMessage.replyMarkup =
//                               ReplyKeyboardButtons().CategoryKeyboard(categoryServiceImpl.getCategory())
//                           sender.execute(sendMessage)
//                       }
//
//                       sendMessage.text=text
//                       sendMessage.replyMarkup=ReplyKeyboardButtons().CategoryKeyboard(categoryServiceImpl.getSubCategory(text))
//                       sender.execute(sendMessage)
//
//
//
//                   }
//               }
//
//
//
//            }

                    }
                }


            }

        } else if (message.hasContact()) {
        if (step == INPUT_CONTACT) {
            sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
            sendMessage.replyMarkup = ReplyKeyboardButtons.MenuKeyboard(
                messageSourceService.getMessage(ORDER_BUTTON),
                messageSourceService.getMessage(ABOUT_US_BUTTON),
                messageSourceService.getMessage(SETTINGS_BUTTON),
                messageSourceService.getMessage(ADD_LOCATION_BUTTON)
            )
            sender.execute(sendMessage)

            userServiceImpl.setStep(chatId, MENU)
        }

    }

}

}







