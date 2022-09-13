package com.example.order_food.Buttons


import com.example.order_food.Entity.OrderItem
import com.example.order_food.enums.CallbackType.*
import com.example.order_food.enums.Language
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
        getOrderItemsList:MutableList<OrderItem>
    ): ReplyKeyboard {
        val line = arrayListOf<List<InlineKeyboardButton>>()
        for (i in getOrderItemsList) {
            val foodName = InlineKeyboardButton(foodServiceImpl.nameFindById(i.food.id!!))
            foodName.callbackData="332"
            val plus = InlineKeyboardButton("+")
            plus.callbackData = "+"
            val minus = InlineKeyboardButton("-")
            minus.callbackData = "-"
            val cross = InlineKeyboardButton("x")
            cross.callbackData = "x"


            line.add((getRow(foodName, plus, minus, cross)))


        }

        val order = InlineKeyboardButton("\uD83D\uDE96 buyurtma berish")
        order.callbackData ="$I_ORDER"
        line.add(getRow(order))


        board.keyboard = line

        return board


    }

    private fun getRow(vararg buttons: InlineKeyboardButton): List<InlineKeyboardButton> {
        return buttons.toMutableList()
    }
}