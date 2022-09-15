package com.example.order_food.handlers

import com.example.order_food.*
import com.example.order_food.Buttons.InlineKeyboardButtons
import com.example.order_food.Buttons.ReplyKeyboardButtons
import com.example.order_food.controllers.MyFeignClient
import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.OrderCreateDto
import com.example.order_food.dtos.OrderItemCreateDto
import com.example.order_food.enums.Language.RU
import com.example.order_food.enums.Language.UZ
import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.enums.Step.*
import com.example.order_food.repository.CategoryRepository
import com.example.order_food.repository.FoodRepository
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.*
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendLocation
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
   private val categoryRepository: CategoryRepository,
    private val telegramConfig: MainBotBot.TelegramConfig,
    private val myFeignClient: MyFeignClient,
    private val addressServiceImpl: AddressServiceImpl,
    private val foodRepository: FoodRepository

    ) {
    fun handle(sender: AbsSender, message: Message) {
        val text = message.text
        val telegramUser = message.from
        val chatId = telegramUser.id.toString()
        var user = userServiceImpl.saveUser(chatId)
        val step = user.step
        val lang = user.lang
        val cache = user.cache
        val sendMessage = SendMessage()
        sendMessage.enableHtml(true)
        sendMessage.chatId = chatId

        if (message.hasText()) {
            when (text) {
                "/start" -> {
                    when (step) {
                        START -> {
                            sendMessage.text = messageSourceService.getMessage(CHOOSE_LANGUAGE_MESSAGE)
                            sendMessage.replyMarkup = InlineKeyboardButtons.languageInlineKeyboard()
                            sender.execute(sendMessage)
                            user.step = LANG
                            userServiceImpl.update(user)

                        }
                        MENU,
                        S_ORDER_BUTTON,
                        S_ADD_LOCATION_BUTTON,
                        S_SETTINGS_BUTTON,
                        SEND_LOCATION,
                        S_CONFIRMATION_BUTTON -> {

                            if (lang == UZ) { LocaleContextHolder.setLocale(Locale(UZ.code)) }
                            else if (lang == RU) { LocaleContextHolder.setLocale(Locale(RU.code)) }

                            sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                            sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                arrayOf(messageSourceService.getMessage(ORDER_BUTTON)),
                                arrayOf(
                                    messageSourceService.getMessage(ABOUT_US_BUTTON),
                                    messageSourceService.getMessage(SETTINGS_BUTTON)
                                ),
                                arrayOf(messageSourceService.getMessage(ADD_LOCATION_BUTTON)),
                            )
                            sender.execute(sendMessage)
                            user.step = MENU
                            user.cache=null
                            userServiceImpl.update(user)
                        }
                    }
                }

                messageSourceService.getMessage(CONFIRMATION_BUTTON) -> {
                   if(step==S_CONFIRMATION_BUTTON){
                       sendMessage.chatId = telegramConfig.chanel
                       sendMessage.text = orderInfo(
                           user,
                           orderItemServiceImpl.getOrderItems(user.id!!),
                           addressServiceImpl.getOne(user.id!!).address
                       )

                       val a  = sender.execute(sendMessage).messageId

                       val sendLocation = SendLocation(telegramConfig.chanel,41.317648,69.230585)
                       sendLocation.replyToMessageId=a
                       sender.execute(sendLocation)

                       sendMessage.text = "Buyurtmangiz qabul qilindi tez orada yetqazib beramiz"
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

                       user.step = MENU
                       user.cache=null
                       userServiceImpl.update(user)
                       sender.execute(sendMessage)




                   }

                }
                messageSourceService.getMessage(BACK_BUTTON)->{
                       if(step==S_CONFIRMATION_BUTTON){
                        sendMessage.text=messageSourceService.getMessage(ORDER_BUTTON_MESSAGE)
                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                            categoryServiceImpl.getCategory(),
                            messageSourceService
                        )
                        sender.execute(sendMessage)
                        user.cache = "sss"
                        user.step = S_ORDER_BUTTON
                        userServiceImpl.update(user)
                    }

                }
                else -> {
                    when (step) {
                        MENU -> {
                            when (text) {
                                messageSourceService.getMessage(ORDER_BUTTON) -> {
                                    sendMessage.text = messageSourceService.getMessage(ORDER_BUTTON_MESSAGE)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                        categoryServiceImpl.getCategory(),
                                        messageSourceService
                                    )
                                    sender.execute(sendMessage)
                                    user.cache = "sss"
                                    user.step = S_ORDER_BUTTON
                                    userServiceImpl.update(user)
                                }
                                messageSourceService.getMessage(ABOUT_US_BUTTON) -> {
                                    sendMessage.text=messageSourceService.getMessage(ABOUT_US_MESSAGE)
                                    sender.execute(sendMessage)
                                }
                                messageSourceService.getMessage(SETTINGS_BUTTON) -> {

                                    sendMessage.text=messageSourceService.getMessage(SETTINGS_MESSAGE)
                                    sendMessage.replyMarkup=ReplyKeyboardButtons.settingsKeyboard(messageSourceService)
                                    sender.execute(sendMessage)
                                    user.step=S_SETTINGS_BUTTON
                                    userServiceImpl.update(user)

                                }

                                else->{
                                    sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
                                    sender.execute(sendMessage)
                                }
                            }

                        }


                        S_ORDER_BUTTON -> {
                            if (text == messageSourceService.getMessage(BACK_BUTTON)) {
                                if (cache == "sss") {
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
                                    user.step = MENU
                                    userServiceImpl.update(user)
                                }
                                else if(categoryRepository.existsByName(cache!!)){
                                    sendMessage.text = messageSourceService.getMessage(CONTINUE_ORDER_MESSAGE)
                                    if(categoryRepository.getBackCategory(cache)==null) {
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            categoryRepository.getCategory(),
                                            messageSourceService
                                        )
                                        sender.execute(sendMessage)
                                        user.cache = "sss"
                                        user.step = S_ORDER_BUTTON
                                        userServiceImpl.update(user)
                                    }else if(categoryRepository.getBackCategory(cache)!=null){
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            categoryRepository.getLastCategory2(cache),
                                            messageSourceService
                                        )
                                        sender.execute(sendMessage)
                                        user.cache = categoryRepository.getBackCategory(cache)
                                        userServiceImpl.update(user)
                                    }


                                } else if(foodServiceImpl.existsByName(cache)){
                                    sendMessage.text = messageSourceService.getMessage(CONTINUE_ORDER_MESSAGE)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                        foodRepository.getLastFoods(cache),
                                        messageSourceService
                                    )
                                    sender.execute(sendMessage)

                                    user.cache=foodRepository.getBackFood(cache)
                                    userServiceImpl.update(user)

                                }

                            }
                            else if (text == messageSourceService.getMessage(PANNIER_BUTTON)) {
                                sendMessage.text= pannierBody(orderItemServiceImpl.getOrderItems(user.id!!))
                                sendMessage.replyMarkup = InlineKeyboardButtons.pannierInlineKeyboard(foodServiceImpl, orderItemServiceImpl.getOrderItems(user.id!!),messageSourceService)
                                sender.execute(sendMessage)
                            }
                            else {
                                if (existsTextInCacheList(text, categoryServiceImpl.getSubCategory(cache!!))) {
                                    if (categoryRepository.existsByName(text) && foodServiceImpl.getFoods(text)!!.isNotEmpty()) {
                                        sendMessage.text = text
                                        user.cache = text
                                         userServiceImpl.update(user)
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            foodServiceImpl.getFoods(text)!!,
                                            messageSourceService
                                        )
                                        sender.execute(sendMessage)
                                    }
                                    else{
                                        sendMessage.text = text
                                        user.cache = text
                                       userServiceImpl.update(user)
                                        sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                            categoryServiceImpl.getSubCategory(text)!!,
                                            messageSourceService
                                        )
                                        sender.execute(sendMessage)
                                    }

                                }
                                else if(existsTextInCacheList(text,foodServiceImpl.getFoods(cache))){
                                    sendMessage.text = text
                                    user.cache = text
                                    userServiceImpl.update(user)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.countFoodCategory(
                                        arrayOf("1","2","3"),
                                        arrayOf("4","5","6"),
                                        arrayOf("7","8","9"),
                                        arrayOf(messageSourceService.getMessage(BACK_BUTTON),messageSourceService.getMessage(PANNIER_BUTTON))
                                    )
                                    sender.execute(sendMessage)
                                }
                                else if(foodServiceImpl.existsByName(cache)){
                                    when(text){
                                        "1","2","3","4","5","6","7","8","9" ->{ orderItemServiceImpl.create(OrderItemCreateDto(orderServiceImpl.OrderdfidnByUserId(user.id!!).id!!, foodServiceImpl.findByName(cache).id!!, text.toInt()))
                                            sendMessage.text =messageSourceService.getMessage(CHOOSE_QUANTITY_MESSAGE)
                                            sendMessage.replyMarkup = ReplyKeyboardButtons.categoryKeyboard(
                                                categoryServiceImpl.getCategory(), messageSourceService)
                                             sender.execute(sendMessage)
                                             user.cache = "sss"
                                             user.step = S_ORDER_BUTTON
                                            userServiceImpl.update(user)

                                        }
                                    }


                                }
                                else{
                                    sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
                                    sender.execute(sendMessage)

                                }

                            }

                        }
                        S_ADD_LOCATION_BUTTON -> {}
                        S_SETTINGS_BUTTON -> {
                            when (text) {
                                messageSourceService.getMessage(CHOOSE_LANGUAGE_BUTTON) -> {
                                    sendMessage.text=messageSourceService.getMessage(CHOOSE_LANGUAGE_BUTTON_MESSAGE)
                                    sendMessage.replyMarkup=ReplyKeyboardButtons.chooseLangKeyboard()
                                    sender.execute(sendMessage)


                                }
                                messageSourceService.getMessage(BACK_BUTTON) -> {
                                    if (lang == UZ) { LocaleContextHolder.setLocale(Locale(UZ.code)) }
                                    else if (lang == RU) { LocaleContextHolder.setLocale(Locale(RU.code)) }
                                    sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                        arrayOf(messageSourceService.getMessage(ORDER_BUTTON)),
                                        arrayOf(
                                            messageSourceService.getMessage(ABOUT_US_BUTTON),
                                            messageSourceService.getMessage(SETTINGS_BUTTON)
                                        ),
                                        arrayOf(messageSourceService.getMessage(ADD_LOCATION_BUTTON)),
                                    )
                                    sender.execute(sendMessage)
                                    user.step=MENU
                                    userServiceImpl.update(user)

                                }
                                "\uD83C\uDDFA\uD83C\uDDFF O'zbek" -> {
                                    user.lang=UZ
                                    userServiceImpl.update(user)
                                    LocaleContextHolder.setLocale(Locale(UZ.code))
                                    sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                        arrayOf(messageSourceService.getMessage(ORDER_BUTTON)),
                                        arrayOf(
                                            messageSourceService.getMessage(ABOUT_US_BUTTON),
                                            messageSourceService.getMessage(SETTINGS_BUTTON)
                                        ),
                                        arrayOf(messageSourceService.getMessage(ADD_LOCATION_BUTTON)),
                                    )
                                    sender.execute(sendMessage)
                                    user.step=MENU
                                    userServiceImpl.update(user)

                                }
                                "\uD83C\uDDF7\uD83C\uDDFA Русский" -> {
                                    user.lang=RU
                                    userServiceImpl.update(user)
                                    LocaleContextHolder.setLocale(Locale(RU.code))
                                    sendMessage.text = messageSourceService.getMessage(INPUT_MENU_MESSAGE)
                                    sendMessage.replyMarkup = ReplyKeyboardButtons.menuKeyboard(
                                        arrayOf(messageSourceService.getMessage(ORDER_BUTTON)),
                                        arrayOf(
                                            messageSourceService.getMessage(ABOUT_US_BUTTON),
                                            messageSourceService.getMessage(SETTINGS_BUTTON)
                                        ),
                                        arrayOf(messageSourceService.getMessage(ADD_LOCATION_BUTTON)),
                                    )
                                    sender.execute(sendMessage)
                                    user.step=MENU
                                    userServiceImpl.update(user)

                                }
                            }
                        }


                    }
                }

            }

        }
        else if (message.hasContact()) {
            if (step == INPUT_CONTACT) {
                val firstName = message.from.firstName
                val lastName = message.from.lastName
                val userName = message.from.userName

                if (lastName != null) {
                    user.fullName = "$firstName $lastName"
                } else {
                    user.fullName = firstName
                }

                user.phoneNumber = message.contact.phoneNumber
                user.step=MENU
                user = userServiceImpl.update(user)
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
                orderServiceImpl.create(OrderCreateDto(user.id!!.toLong(), message.contact.phoneNumber))
            }
            else{
                sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
                sender.execute(sendMessage)
            }

        }
        else if (message.hasLocation()){

            if(step==SEND_LOCATION){
                user.cache = message.messageId.toString()
                user.step=S_CONFIRMATION_BUTTON
                user = userServiceImpl.update(user)
                val geocode = "${message.location.longitude},${message.location.latitude}"
                val location = myFeignClient.getLocationInfo(geocode,"1",lang.toString()).response.geoObjectCollection.featureMember
                location.map {
                    sendMessage.text = orderInfo(
                        user,
                        orderItemServiceImpl.getOrderItems(user.id!!),
                      it.geoObject.metaDataProperty.geocoderMetaData.text

                    )
                    sendMessage.replyMarkup = ReplyKeyboardButtons.confirm(messageSourceService)
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


            }else{
                sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
                sender.execute(sendMessage)
            }

        }
        else { sendMessage.text = messageSourceService.getMessage(WRONG_COMMAND_MESSAGE)
            sender.execute(sendMessage) }
    }
}











