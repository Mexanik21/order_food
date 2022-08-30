package com.example.order_food.Buttons

import com.example.order_food.enums.Language
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object MarkupButtons {


    fun shareContact(name:String): ReplyKeyboard {
        val board = ReplyKeyboardMarkup()
        val phone = KeyboardButton(name)
        phone.requestContact = true
        board.selective = null
        board.inputFieldPlaceholder="contact "
        board.oneTimeKeyboard=false
        board.resizeKeyboard =true
        val row = KeyboardRow()
        row.add(phone)
        board.keyboard = listOf(row)
        return board
    }
}