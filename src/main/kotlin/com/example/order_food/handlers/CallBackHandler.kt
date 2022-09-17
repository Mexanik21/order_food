package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.enums.CallbackType.*
import com.example.order_food.enums.Language.*
import com.example.order_food.enums.LocalizationTextKey.*

import com.example.order_food.enums.Step.*
import com.example.order_food.pannierBody
import com.example.order_food.repository.OrderItemRepository
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.FoodServiceImpl
import com.example.order_food.service.impl.OrderItemServiceImpl
import com.example.order_food.service.impl.UserServiceImpl
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText.EditMessageTextBuilder
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender
import java.util.*


@Service
class CallBackHandler(
    private val userServiceImpl: UserServiceImpl,
    private val messageSourceService: MessageSourceService,
    private val orderItemRepository: OrderItemRepository,
    private val orderItemServiceImpl: OrderItemServiceImpl,
    private val foodServiceImpl: FoodServiceImpl

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
                            user.lang=UZ

                        }else if(data=="$I_RU"){
                            LocaleContextHolder.setLocale(Locale(RU.code))
                            user.lang=RU
                        }

                        sendMessage.text=messageSourceService.getMessage(INPUT_CONTACT_MESSAGE)
                        sendMessage.replyMarkup=ReplyKeyboardButtons.shareContact(messageSourceService.getMessage(ENTER_CONTACT_BUTTON))
                        sender.execute(sendMessage)
                        user.step=INPUT_CONTACT
                        userServiceImpl.update(user)

                }


            }

            "$I_ORDER" ->{
                if(step==S_ORDER_BUTTON){
                    sendMessage.text=messageSourceService.getMessage(LOCATION_BUTTON_MESSAGE)
                    sendMessage.replyMarkup= ReplyKeyboardButtons.myLocation(messageSourceService)
                    sender.execute(sendMessage)
                    user.step=SEND_LOCATION
                    userServiceImpl.update(user)
                }else{
                    sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
                    sender.execute(sendMessage)
                }


            }


            else->{
                if(step==S_ORDER_BUTTON ){

                    when(data.substring(0,1)){
                        "x"->{ orderItemRepository.deleteById(data.substring(2).toLong()) }
                        "+"->{orderItemRepository.addCountOrderItem(data.substring(2).toLong())}
                        "-"->{
                            if(orderItemRepository.reduceCountOrderItem(data.substring(2).toLong()).count==0){
                                orderItemRepository.deleteById(data.substring(2).toLong())
                            }
                        }
                    }
                    val orderItems=orderItemServiceImpl.getOrderItems(user.id!!)
                    if(orderItems.isEmpty()){
                        sender.execute(DeleteMessage(message.chatId.toString(),message.messageId))
                        sendMessage.text = "Savat bo'sh, Iltimos buyurtmani davom enttiring!"
                        sender.execute(sendMessage)
                    }else{
                        val editMessage = EditMessageText()
                        editMessage.chatId = message.chatId.toString()
                        editMessage.messageId = message.messageId
                        editMessage.replyMarkup = InlineKeyboardButtons.pannierInlineKeyboard(foodServiceImpl,orderItems ,messageSourceService)
                        editMessage.text = pannierBody(orderItemServiceImpl.getOrderItems(user.id!!))
                        sender.execute(editMessage)

                    }

                }
            }


            }




        }


    }


