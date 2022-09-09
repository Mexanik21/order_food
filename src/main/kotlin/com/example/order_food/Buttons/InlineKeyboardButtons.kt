package com.example.order_food.Buttons


import com.example.order_food.Entity.OrderItem
import com.example.order_food.enums.CallbackType
import com.example.order_food.enums.Language
import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.service.MessageSourceService
import com.example.order_food.service.impl.FoodServiceImpl
import com.example.order_food.service.impl.OrderServiceImpl
import com.example.order_food.service.impl.UserServiceImpl
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.*
import kotlin.streams.toList


object InlineKeyboardButtons {
    private val board = InlineKeyboardMarkup()
    fun languageInlineKeyboard(): ReplyKeyboard {
        val uz = InlineKeyboardButton(Language.UZ.text)
        uz.callbackData = "${CallbackType.UZ}"
        val ru = InlineKeyboardButton(Language.RU.text)
        ru.callbackData = "${CallbackType.RU}"
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

        val zakas = InlineKeyboardButton("\uD83D\uDE96 buyurtma berish")
        zakas.callbackData = "zakas"
        line.add(getRow(zakas))


        board.keyboard = line

        return board


    }

    private fun getRow(vararg buttons: InlineKeyboardButton): List<InlineKeyboardButton> {
        return buttons.toMutableList()
    }
}