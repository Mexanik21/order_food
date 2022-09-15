package com.example.order_food.handlers

import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.CallbackType.*
import com.example.order_food.enums.Language.*
import com.example.order_food.enums.LocalizationTextKey.*

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
        val user = userServiceImpl.saveUser(telegramId)
        val step = user.step
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = telegramId

        when(data){

            "$I_UZ","$I_RU"->{
                if(step==LANG){
                        if(data=="$I_UZ"){
                            LocaleContextHolder.setLocale(Locale(UZ.code))
                            userServiceImpl.setLang(telegramId,UZ)

                        }else if(data=="$I_RU"){
                            LocaleContextHolder.setLocale(Locale(RU.code))
                            userServiceImpl.setLang(telegramId,RU)
                        }

                        sendMessage.text=messageSourceService.getMessage(INPUT_CONTACT_MESSAGE)
                        sendMessage.replyMarkup=ReplyKeyboardButtons.shareContact(messageSourceService.getMessage(ENTER_CONTACT_BUTTON))
                        sender.execute(sendMessage)
                        userServiceImpl.setStep(telegramId,INPUT_CONTACT)

                }


            }

            "$I_ORDER" ->{

                if(step==S_ORDER_BUTTON){
                    sendMessage.text=messageSourceService.getMessage(LOCATION_BUTTON_MESSAGE)
                    sendMessage.replyMarkup= ReplyKeyboardButtons.myLocation(messageSourceService)
                    sender.execute(sendMessage)
                    user.step=SEND_LOCATION
                    userServiceImpl.update(user)
                }


            }

            "$ADD"->{
                if(step==S_ORDER_BUTTON ){


                }

            }
            "$REDUCE"->{
                if(step==S_ORDER_BUTTON ){

                }

            }

            "$CLEAR"->{
                if(step==S_ORDER_BUTTON ){

                }

            }




        }


    }

}
