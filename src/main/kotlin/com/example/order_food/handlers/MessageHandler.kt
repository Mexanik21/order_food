package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.Entity.Order
import com.example.order_food.Entity.OrderItem
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.enums.Step
import com.example.order_food.enums.Language.*
import com.example.order_food.pannierBody
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.repository.OrderRepository
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.*
import org.springframework.context.i18n.LocaleContextHolder

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import java.util.*
import kotlin.collections.HashMap


@Service
class MessageHandler(
    private val userServiceImpl: UserServiceImpl,
    private val messageSourceService: MessageSourceService,
    private val categoryServiceImpl: CategoryServiceImpl,
    private val foodServiceImpl: FoodServiceImpl,
    private val orderServiceImpl: OrderServiceImpl,
    private val orderItemServiceImpl: OrderItemServiceImpl

) {

    fun handle(sender: AbsSender, message: Message) {
        val text = message.text
        val telegramUser = message.from
        val chatId = telegramUser.id.toString()
        var user = userServiceImpl.saveUser(chatId)
        val step = user.step
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = chatId






        if (message.hasText()) {

            when (text) {
                "/start" -> {
                    if (step == Step.START) {
                        sendMessage.text = messageSourceService.getMessage(CHOOSE_LANGUAGE_MESSAGE)
                        sendMessage.replyMarkup = InlineKeyboardButtons.languageInlineKeyboard()
                        sender.execute(sendMessage)
                        userServiceImpl.setStep(chatId, Step.LANG)

                    } else {

                        if(userServiceImpl.getLanguage(chatId)==UZ){
                            LocaleContextHolder.setLocale(Locale(UZ.code))


                        }else if(userServiceImpl.getLanguage(chatId)==RU){
                            LocaleContextHolder.setLocale(Locale(RU.code))

                        }
                        sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                        sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                            arrayOf(
                                messageSourceService.getMessage(ORDER_BUTTON),

                                ),
                            arrayOf(
                                messageSourceService.getMessage(ABOUT_US_BUTTON),
                                messageSourceService.getMessage(SETTINGS_BUTTON)
                            ),
                            arrayOf(

                                messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                            ),

                            )
                        sender.execute(sendMessage)
                        userServiceImpl.setStep(chatId, Step.MENU)
                    }


                }
                else -> {
                    when (step) {
                        Step.MENU -> {
                            if (text == messageSourceService.getMessage(ORDER_BUTTON)) {
                                sendMessage.text = text
                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                    categoryServiceImpl.getCategory(),
                                    messageSourceService
                                )
                                user.cache = null
                                userServiceImpl.update(user)
                                sender.execute(sendMessage)
                            } else if (text == messageSourceService.getMessage(BACK_BUTTON)) {

                                if(user.cache==null){
                                    sendMessage.text = text
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                        arrayOf(
                                            messageSourceService.getMessage(ORDER_BUTTON),

                                            ),
                                        arrayOf(
                                            messageSourceService.getMessage(ABOUT_US_BUTTON),
                                            messageSourceService.getMessage(SETTINGS_BUTTON)
                                        ),
                                        arrayOf(

                                            messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                                        ),

                                        )
                                    sender.execute(sendMessage)
                                }else{
                                    val lastCategory = categoryServiceImpl.getLastCategory(user.cache!!)
                                   if(lastCategory.isNotEmpty()){
                                       lastCategory.let {
                                           sendMessage.text = text
                                           sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                               it,
                                               messageSourceService
                                           )
                                           user.cache = it[0]
                                           user = userServiceImpl.update(user)
                                           sender.execute(sendMessage)
                                       }

                                   }

                                    if (lastCategory.isEmpty()) {
                                        if (categoryServiceImpl.getCategoryEmptyParentId(user.cache!!) != null) {
                                            sendMessage.text = text
                                            sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                                categoryServiceImpl.getCategory(),
                                                messageSourceService
                                            )
                                            user.cache = categoryServiceImpl.getCategory()[0]
                                            user = userServiceImpl.update(user)
                                            sender.execute(sendMessage)
                                        } else {
                                            user.cache = null
                                            userServiceImpl.update(user)
                                            sendMessage.text = text
                                            sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                                arrayOf(
                                                    messageSourceService.getMessage(ORDER_BUTTON),

                                                    ),
                                                arrayOf(
                                                    messageSourceService.getMessage(ABOUT_US_BUTTON),
                                                    messageSourceService.getMessage(SETTINGS_BUTTON)
                                                ),
                                                arrayOf(

                                                    messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                                                ),

                                                )
                                            sender.execute(sendMessage)
                                        }
                                    }
                                }
                            }else if(text == messageSourceService.getMessage(PANNIER_BUTTON)){
                                sendMessage.text= pannierBody( orderItemServiceImpl.getOrderItems(user.id!!))
                                sendMessage.replyMarkup=InlineKeyboardButtons.pannierInlineKeyboard(foodServiceImpl, orderItemServiceImpl.getOrderItems(user.id!!))
                                sender.execute(sendMessage)




                            } else {
                                if (categoryServiceImpl.getSubCategory(text)!!.isNotEmpty()) {
                                    sendMessage.text = text
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                        categoryServiceImpl.getSubCategory(text)!!,
                                        messageSourceService
                                    )
                                    user.cache = categoryServiceImpl.getSubCategory(text)!![0]
                                    user = userServiceImpl.update(user)
                                    sender.execute(sendMessage)
                                } else {
                                    if(foodServiceImpl.existsByName(text)){
                                        sendMessage.text = text
                                        user.cache = text
                                        user = userServiceImpl.update(user)
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.countFoodCategory(
                                            arrayOf("1","2","3"),
                                            arrayOf("4","5","6"),
                                            arrayOf("7","8","9"),
                                            arrayOf(messageSourceService.getMessage(BACK_BUTTON),messageSourceService.getMessage(
                                                PANNIER_BUTTON
                                            ))
                                        )
                                        sender.execute(sendMessage)
                                    }
                                    else if(foodServiceImpl.existsByName(user.cache!!)) {
                                        when(text){
                                            "1","2","3","4","5","6","7","8","9" ->{

                                                orderItemServiceImpl.create(OrderItemCreateDto(
                                                    orderServiceImpl.OrderdfidnByUserId(user.id!!).id!!,
                                                    foodServiceImpl.findByName(user.cache!!).id!!,
                                                    text.toInt()


                                                ))











                                            }
                                        }

                                    }else{
                                        sendMessage.text = text
                                        user.cache = foodServiceImpl.getFoods(text)[0]
                                        user = userServiceImpl.update(user)
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            foodServiceImpl.getFoods(text),
                                            messageSourceService
                                        )
                                        sender.execute(sendMessage)
                                    }


                                }


                            }


                        }


                    }
                }

            }

        }else if (message.hasContact()) {
            if (step == Step.INPUT_CONTACT) {
                sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                    arrayOf(
                        messageSourceService.getMessage(ORDER_BUTTON),

                        ),
                    arrayOf(
                        messageSourceService.getMessage(ABOUT_US_BUTTON),
                        messageSourceService.getMessage(SETTINGS_BUTTON)
                    ),
                    arrayOf(

                        messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                    ),

                    )
                sender.execute(sendMessage)

                userServiceImpl.setStep(chatId, Step.MENU)
            }

            orderServiceImpl.create(OrderCreateDto(user.id!!.toLong(),message.contact.phoneNumber))

        }




    }





}





