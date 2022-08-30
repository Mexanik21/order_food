package com.example.order_food.Buttons



import com.example.order_food.enums.CallbackType
import com.example.order_food.enums.Language
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.*
import kotlin.streams.toList


class InlineKeyboardButtons {
    private val board = InlineKeyboardMarkup()
    fun LanguageInlineKeyboard(): ReplyKeyboard? {
        val uz = InlineKeyboardButton("\uD83C\uDDFA\uD83C\uDDFF Uzb")
        uz.callbackData = "${CallbackType.UZ}"
        val ru = InlineKeyboardButton("\uD83C\uDDF7\uD83C\uDDFA Рус")
        ru.callbackData = "${CallbackType.RU}"
        board.keyboard = listOf(getRow(uz,ru))
        return board
    }

    private fun getRow(vararg buttons: InlineKeyboardButton): List<InlineKeyboardButton> {
        return Arrays.stream(buttons).toList()
    }



}