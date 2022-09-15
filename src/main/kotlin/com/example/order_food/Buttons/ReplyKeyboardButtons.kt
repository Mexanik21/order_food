package com.example.order_food.Buttons



import com.example.order_food.enums.LocalizationTextKey.*
import com.example.order_food.service.MessageSourceService
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardButtons {
    private val board = ReplyKeyboardMarkup()

    fun shareContact(name:String): ReplyKeyboard {
        val phone = KeyboardButton(name)
        phone.requestContact = true
        board.selective = null
        board.oneTimeKeyboard=false
        board.resizeKeyboard =true
        val row = KeyboardRow()
        row.add(phone)
        board.keyboard = listOf(row)
        return board
    }

    fun myLocation(messageSourceService: MessageSourceService):ReplyKeyboard{
        val location=KeyboardButton(messageSourceService.getMessage(LOCATION_BUTTON))
        location.requestLocation=true
        val row = KeyboardRow()
        row.add(location)
        board.resizeKeyboard =true
        board.keyboard= listOf(row)


        return board
    }

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
        val back = KeyboardButton(messageSourceService.getMessage(BACK_BUTTON))
        first.add(back)
        val pannier = KeyboardButton(messageSourceService.getMessage(PANNIER_BUTTON))
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



    fun confirm(messageSourceService: MessageSourceService):ReplyKeyboard{

        val confirm=KeyboardButton(messageSourceService.getMessage(CONFIRMATION_BUTTON))
        val back=KeyboardButton(messageSourceService.getMessage(BACK_BUTTON))
        val row = KeyboardRow()
        val row1=KeyboardRow()
        row.add(confirm)
        row1.add(back)
        board.resizeKeyboard =true
        board.keyboard= listOf(row,row1)

        return board
    }

    fun settingsKeyboard(messageSourceService: MessageSourceService): ReplyKeyboardMarkup {
        val confirm=KeyboardButton(messageSourceService.getMessage(CHOOSE_LANGUAGE_BUTTON))
        val back=KeyboardButton(messageSourceService.getMessage(BACK_BUTTON))
        val row = KeyboardRow()
        val row1=KeyboardRow()
        row.add(confirm)
        row1.add(back)
        board.resizeKeyboard =true
        board.keyboard= listOf(row,row1)

        return board
    }

    fun chooseLangKeyboard(): ReplyKeyboardMarkup {
        val uz=KeyboardButton("\uD83C\uDDFA\uD83C\uDDFF O'zbek")
        val ru=KeyboardButton("\uD83C\uDDF7\uD83C\uDDFA Русский")
        val row = KeyboardRow()

        row.add(uz)
        row.add(ru)

        board.resizeKeyboard =true
        board.keyboard= listOf(row)

        return board
    }

}