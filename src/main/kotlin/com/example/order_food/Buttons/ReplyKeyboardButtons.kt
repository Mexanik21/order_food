package com.example.order_food.Buttons


import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.service.MessageSourceService
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardButtons {
    private val board = ReplyKeyboardMarkup()

    fun menuKeyboard(vararg rows: Array<String>): ReplyKeyboard {
        board.keyboard = mutableListOf()
        rows.forEach {
            val row = KeyboardRow()
            it.forEach { item ->
                row.add(item)
            }
            board.keyboard.add(row)
        }
        board.selective = true
        board.resizeKeyboard = true
        board.resizeKeyboard = true
        board.selective = true
        return board
    }



    fun categoryKeyboard(categoryList: MutableList<String>, messageSourceService: MessageSourceService): ReplyKeyboard {
        val first = KeyboardRow()
        val back = KeyboardButton(messageSourceService.getMessage(LocalizationTextKey.BACK_BUTTON))
        first.add(back)
        val pannier = KeyboardButton(messageSourceService.getMessage(LocalizationTextKey.PANNIER_BUTTON))
        first.add(pannier)

        val line = arrayListOf(first)

        categoryList.chunked(2) {
            val row = KeyboardRow()
            it.forEach { c ->
                row.add(c)
            }
            line.add(row)
        }


        board.selective = true
        board.resizeKeyboard = true
        board.keyboard = line
        board.resizeKeyboard = true
        board.selective = true
        return board


    }


    fun countFoodCategory(vararg rows: Array<String>):ReplyKeyboard {

        board.keyboard = mutableListOf()
        rows.forEach {
            val row = KeyboardRow()
            it.forEach { item ->
                row.add(item)
            }
            board.keyboard.add(row)
        }
        board.selective = true
        board.resizeKeyboard = true
        board.resizeKeyboard = true
        board.selective = true
        return board
    }
}