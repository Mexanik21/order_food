package com.example.order_food.Buttons


import com.example.order_food.Entity.OrderItem
import com.example.order_food.enums.CallbackType.*
import com.example.order_food.enums.Language
import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.FoodServiceImpl
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


object InlineKeyboardButtons {
    private val board = InlineKeyboardMarkup()
    fun languageInlineKeyboard(): ReplyKeyboard {
        val uz = InlineKeyboardButton(Language.UZ.text)
        uz.callbackData = "$I_UZ"
        val ru = InlineKeyboardButton(Language.RU.text)
        ru.callbackData = "$I_RU"
        board.keyboard = listOf(getRow(uz, ru))
        return board
    }


    fun pannierInlineKeyboard(
        foodServiceImpl: FoodServiceImpl,
        getOrderItemsList:MutableList<OrderItem>,
        messageSourceService: MessageSourceService
    ): InlineKeyboardMarkup {
        val line = arrayListOf<List<InlineKeyboardButton>>()
        for (i in getOrderItemsList) {
            val foodName = InlineKeyboardButton(foodServiceImpl.nameFindById(i.food.id!!))
            foodName.callbackData= i.food.name
            val add = InlineKeyboardButton("+")
            add.callbackData = "+|${i.id}"
            val reduce = InlineKeyboardButton("-")
            reduce.callbackData = "-|${i.id}"
            val clear = InlineKeyboardButton("x")
            clear.callbackData="x|${i.id}"



            line.add((getRow(foodName, add, reduce, clear)))


        }

        val order = InlineKeyboardButton(messageSourceService.getMessage(LocalizationTextKey.PLACING_ORDER_BUTTON))
        order.callbackData ="$I_ORDER"
        line.add(getRow(order))


        board.keyboard = line

        return board


    }

    private fun getRow(vararg buttons: InlineKeyboardButton): List<InlineKeyboardButton> {
        return buttons.toMutableList()
    }
}