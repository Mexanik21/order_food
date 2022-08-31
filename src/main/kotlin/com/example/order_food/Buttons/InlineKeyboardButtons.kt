package com.example.order_food.Buttons



import com.example.order_food.enums.CallbackType
import com.example.order_food.enums.Language
import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.service.MessageSourceService
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.*
import kotlin.streams.toList


object InlineKeyboardButtons {
    private val board = InlineKeyboardMarkup()
    fun LanguageInlineKeyboard(): ReplyKeyboard? {
        val uz = InlineKeyboardButton(Language.UZ.text)
        uz.callbackData = "${CallbackType.UZ}"
        val ru = InlineKeyboardButton(Language.RU.text)
        ru.callbackData = "${CallbackType.RU}"
        board.keyboard = listOf(getRow(uz,ru))
        return board
    }

    private fun getRow(vararg buttons: InlineKeyboardButton): List<InlineKeyboardButton> {
        return Arrays.stream(buttons).toList()
    }



}