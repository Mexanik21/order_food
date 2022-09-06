package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.enums.Step.*
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.CategoryServiceImpl
import com.example.order_food.service.impl.FoodServiceImpl
import com.example.order_food.service.impl.UserServiceImpl
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender


@Service
class MessageHandler(
    private val userServiceImpl: UserServiceImpl,
    private val messageSourceService: MessageSourceService,
    private val categoryServiceImpl: CategoryServiceImpl,
    private val foodServiceImpl: FoodServiceImpl,
    private val categoryRepository: CategoryRepository

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
                    if (step == START) {
                        sendMessage.text = messageSourceService.getMessage(CHOOSE_LANGUAGE_MESSAGE)
                        sendMessage.replyMarkup = InlineKeyboardButtons.languageInlineKeyboard()
                        sender.execute(sendMessage)
                        userServiceImpl.setStep(chatId, LANG)

                    } else {
                        sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                        ReplyKeyboardButtons.menuKeyboard(
                            arrayOf(
                                messageSourceService.getMessage(LocalizationTextKey.ORDER_BUTTON),
                                messageSourceService.getMessage(ABOUT_US_BUTTON),
                            ),
                            arrayOf(
                                messageSourceService.getMessage(SETTINGS_BUTTON),
                                messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                            ),
                        )
                        sender.execute(sendMessage)
                        userServiceImpl.setStep(chatId, MENU)
                    }


                }
                else -> {
                    when (step) {
                        MENU -> {
                            if (text == messageSourceService.getMessage(LocalizationTextKey.ORDER_BUTTON)) {
                                sendMessage.text = text
                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                    categoryServiceImpl.getCategory(),
                                    messageSourceService
                                )
                                sender.execute(sendMessage)
                            } else if (text == messageSourceService.getMessage(BACK_BUTTON)) {
                                val lastCategory = categoryServiceImpl.getLastCategory(user.cache!!)

                                if(lastCategory!!.isNotEmpty()){
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

                                if(lastCategory.isEmpty()){

                                    if(categoryRepository.getCategoryEmptyParentId(user.cache!!) != null){
                                        sendMessage.text = text
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            categoryServiceImpl.getCategory(),
                                            messageSourceService
                                        )
                                        user.cache = categoryServiceImpl.getCategory()[0]
                                        user= userServiceImpl.update(user)
                                        sender.execute(sendMessage)
                                    } else{
                                        user.cache=null
                                        userServiceImpl.update(user)
                                        sendMessage.text = text
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.enuKeyboard(
                                            messageSourceService.getMessage(LocalizationTextKey.ORDER_BUTTON),
                                            messageSourceService.getMessage(ABOUT_US_BUTTON),
                                            messageSourceService.getMessage(SETTINGS_BUTTON),
                                            messageSourceService.getMessage(ADD_LOCATION_BUTTON)
                                        )
                                        sender.execute(sendMessage)
                                    }
                                }




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

        }else if (message.hasContact()) {
                if (step == INPUT_CONTACT) {
                    sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                    sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                        arrayOf(
                            messageSourceService.getMessage(LocalizationTextKey.ORDER_BUTTON),

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

                    userServiceImpl.setStep(chatId, MENU)
                }

            }

        }

    }




















































//                        Step.ORDER_BUTTON -> {
//
//                            if (text == messageSourceService.getMessage(BACK_BUTTON)) {
//                                sendMessage.text = text
//                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
//                                    categoryServiceImpl.getCategory(),
//                                    messageSourceService
//                                )
//                                sender.execute(sendMessage)
//                                userServiceImpl.setStep(chatId, MENU)
//
//                            } else {
//                                if (categoryServiceImpl.getSubCategory(text)!!.isNotEmpty()) {
//                                    sendMessage.text = text
//                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
//                                        categoryServiceImpl.getSubCategory(text)!!,
//                                        messageSourceService
//                                    )
//                                    user.cache= categoryServiceImpl.getSubCategory(text)!![0]
//                                    user = userServiceImpl.update(user)
//                                    sender.execute(sendMessage)
//                                } else {
//                                    sendMessage.text = text
//                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
//                                        foodServiceImpl.getFoods(text),
//                                        messageSourceService
//                                    )
//                                    sender.execute(sendMessage)
//
//
//                                }
//                            }
//                        }






