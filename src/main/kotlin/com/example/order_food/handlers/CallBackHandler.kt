package com.example.order_food.handlers

import com.example.order_food.Buttons.MarkupButtons


import com.example.order_food.enums.CallbackType.*
import com.example.order_food.enums.Language
import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.enums.Step.*
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.UserServiceImpl
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender
import java.util.*


@Service
class CallBackHandler(
    private val userServiceImpl: UserServiceImpl,
    private val messageSourceService: MessageSourceService

) {

    fun handle(sender:AbsSender,callbackQuery: CallbackQuery) {
        val data = callbackQuery.data
        val message = callbackQuery.message
        val telegramId = message.chatId.toString()
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = telegramId

       when(data){

           "$UZ","$RU"->{
               if(data=="$UZ"){
                   LocaleContextHolder.setLocale(Locale(Language.UZ.code))
                   userServiceImpl.setLang(telegramId,Language.UZ)

               }else if(data=="$RU"){
                   LocaleContextHolder.setLocale(Locale(Language.RU.code))
                   userServiceImpl.setLang(telegramId,Language.RU)
               }

               sendMessage.text=messageSourceService.getMessage(LocalizationTextKey.INPUT_CONTACT_MESSAGE)
               sendMessage.replyMarkup=MarkupButtons.shareContact(messageSourceService.getMessage(LocalizationTextKey.ENTER_CONTACT_BUTTON))
               sender.execute(sendMessage)
               userServiceImpl.setStep(telegramId,INPUT_CONTACT)


           }


       }


    }

}
