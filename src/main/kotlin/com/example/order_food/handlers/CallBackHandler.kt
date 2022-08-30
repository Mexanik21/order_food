package com.example.order_food.handlers

import com.example.order_food.Buttons.MarkupButtons
import com.example.order_food.ConstRU
import com.example.order_food.ConstUZ
import com.example.order_food.enums.CallbackType
import com.example.order_food.enums.Language
import com.example.order_food.enums.Step
import com.example.order_food.service.impl.UserServiceImpl
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender


@Service
class CallBackHandler(
    private val userServiceImpl: UserServiceImpl

) {


    fun handle(sender:AbsSender,callbackQuery: CallbackQuery) {
        val data = callbackQuery.data
        val message = callbackQuery.message
        val telegramId = message.chatId.toString()
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = telegramId

     if(data=="${CallbackType.UZ}"&&userServiceImpl.getStep(telegramId)==Step.START){
         userServiceImpl.setLang(telegramId,Language.UZ)
         userServiceImpl.setStep(telegramId, Step.LANG)
         sendMessage.text=ConstUZ.ENTER_PHONE_MESSAGE
         sendMessage.replyMarkup=MarkupButtons.shareContact(ConstUZ.ENTER_PHONE_BUTTON)
         sender.execute(sendMessage)

      }else if(data=="${CallbackType.RU}"&&userServiceImpl.getStep(telegramId)==Step.START){
         userServiceImpl.setLang(telegramId,Language.RU)
         userServiceImpl.setStep(telegramId, Step.LANG)
         sendMessage.text= ConstRU.ENTER_PHONE_MESSAGE
         sendMessage.replyMarkup=MarkupButtons.shareContact(ConstRU.ENTER_PHONE_BUTTON)
         sender.execute(sendMessage)

     }


   }

}
