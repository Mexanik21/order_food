package com.example.order_food.handlers

import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.MainBotBot
import com.example.order_food.controllers.MyFeignClient
import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.dtos.UserCreateDto
import com.example.order_food.enums.Language.RU
import com.example.order_food.enums.Language.UZ
import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.enums.Step.*
import com.example.order_food.orderInfo
import com.example.order_food.pannierBody
import com.example.order_food.repository.UserRepository
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.*
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ForwardMessage
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import java.util.*


@Service
class MessageHandler(
    private val userServiceImpl: UserServiceImpl,
    private val messageSourceService: MessageSourceService,
    private val categoryServiceImpl: CategoryServiceImpl,
    private val foodServiceImpl: FoodServiceImpl,
    private val orderServiceImpl: OrderServiceImpl,
    private val orderItemServiceImpl: OrderItemServiceImpl,
    private val userRepository: UserRepository,
    private val telegramConfig: MainBotBot.TelegramConfig,
    private val myFeignClient: MyFeignClient,
    private val addressServiceImpl: AddressServiceImpl

    ) {
    fun handle(sender: AbsSender, message: Message) {
        val text = message.text
        val telegramUser = message.from
        val chatId = telegramUser.id.toString()
        var user = userServiceImpl.create(UserCreateDto((chatId)))
        val step = user.step
        val lang=user.lang
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = chatId


        if (message.hasText()) {

            when (text) {
                "/start" -> {
                    when (step) {
                        START-> {
                            sendMessage.text = messageSourceService.getMessage(CHOOSE_LANGUAGE_MESSAGE)
                            sendMessage.replyMarkup = InlineKeyboardButtons.languageInlineKeyboard()
                            sender.execute(sendMessage)
                            user.step=LANG
                            userServiceImpl.update(user)

                        }


                        MENU -> {
                            if (lang == UZ) {
                                LocaleContextHolder.setLocale(Locale(UZ.code))


                            } else if (lang == RU) {
                                LocaleContextHolder.setLocale(Locale(RU.code))

                            }
                            sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                            sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                arrayOf(messageSourceService.getMessage(ORDER_BUTTON)),
                                arrayOf(messageSourceService.getMessage(ABOUT_US_BUTTON),
                                    messageSourceService.getMessage(SETTINGS_BUTTON)),
                                arrayOf(messageSourceService.getMessage(ADD_LOCATION_BUTTON)),
                            )
                            sender.execute(sendMessage)
                            user.step=step
                            userServiceImpl.update(user)
                        }

                           }
                     }
                messageSourceService.getMessage(CONFIRMATION_BUTTON)  -> {

                    sendMessage.chatId=telegramConfig.chanel
                    sendMessage.text= orderInfo(user,orderItemServiceImpl.getOrderItems(user.id!!), addressServiceImpl.getOne(user.id!!).address)
                    sendMessage.replyToMessageId=user.cache!!.toInt()
                    sender.execute(sendMessage)
                    sendMessage.text="----------------------------"
                    sender.execute(sendMessage)


                }


                else -> {
                    when (step) {
                        MENU -> {
                            if (text == messageSourceService.getMessage(ORDER_BUTTON)) {
                                sendMessage.text =messageSourceService.getMessage(ORDER_BUTTON_MESSAGE)
                                sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(categoryServiceImpl.getCategory(), messageSourceService)
                                user.cache = null
                                userServiceImpl.update(user)
                                sender.execute(sendMessage)
                            }
                            else if (text == messageSourceService.getMessage(BACK_BUTTON)) {

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
                            }
                            else if(text == messageSourceService.getMessage(PANNIER_BUTTON)){
                                sendMessage.text= pannierBody( orderItemServiceImpl.getOrderItems(user.id!!))
                                sendMessage.replyMarkup=InlineKeyboardButtons.pannierInlineKeyboard(foodServiceImpl, orderItemServiceImpl.getOrderItems(user.id!!))
                                sender.execute(sendMessage)




                            }
                            else { if (categoryServiceImpl.getSubCategory(text)!!.isNotEmpty()) {
                                    sendMessage.text = text
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                        categoryServiceImpl.getSubCategory(text)!!,
                                        messageSourceService
                                    )
                                    user.cache = categoryServiceImpl.getSubCategory(text)!![0]
                                    user = userServiceImpl.update(user)
                                    sender.execute(sendMessage)
                                }
                            else { if(foodServiceImpl.existsByName(text)){
                                        sendMessage.text = text
                                        user.cache = text
                                        user = userServiceImpl.update(user)
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.countFoodCategory(
                                            arrayOf("1","2","3"),
                                            arrayOf("4","5","6"),
                                            arrayOf("7","8","9"),
                                            arrayOf(messageSourceService.getMessage(BACK_BUTTON),messageSourceService.getMessage(PANNIER_BUTTON))
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

                                    }
                            else{ sendMessage.text = text
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

        }
        else if (message.hasContact()) {
            if (step == INPUT_CONTACT) {
                val firstName=message.from.firstName
                val lastName=message.from.lastName
                val userName=message.from.userName

                if(lastName!=null){
                    user.fullName="$firstName $lastName"
                }else{
                    user.fullName=firstName
                }

                user.phoneNumber=message.contact.phoneNumber
                user=userServiceImpl.update(user)
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

                userServiceImpl.setStep(chatId, MENU)
            }

            orderServiceImpl.create(OrderCreateDto(user.id!!.toLong(),message.contact.phoneNumber))

        }
        else if(message.hasLocation()){


            user.cache=message.messageId.toString()
            user = userServiceImpl.update(user)
            val r="${message.location.longitude},${message.location.latitude}"

           val location=myFeignClient.getLocationInfo(r,"1").response.geoObjectCollection.featureMember



            location.map {
         sendMessage.text= orderInfo(user,orderItemServiceImpl.getOrderItems(user.id!!), it.geoObject.metaDataProperty.geocoderMetaData.text)
         sendMessage.replyMarkup=ReplyKeyboardButtons.confirm(messageSourceService)
         sender.execute(sendMessage)
         addressServiceImpl.create(
             AddressCreateDto(
             user.id!!,
             it.geoObject.metaDataProperty.geocoderMetaData.text,
             "",
             message.location.latitude.toString(),
             message.location.longitude.toString()
         )
         )



     }






           }
        else{

            sendMessage.text=messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
             sender.execute(sendMessage)

        }


    }

}











