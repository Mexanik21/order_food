package com.example.order_food.Buttons


import com.example.order_food.enums.LocalizationTextKey
import com.example.order_food.service.MessageSourceService
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object ReplyKeyboardButtons {
    private val board = ReplyKeyboardMarkup()
    fun MenuKeyboard(orderT:String,contactUsT:String,settingsT:String,locationT:String ): ReplyKeyboard? {
        val row1 = KeyboardRow()
        val row2 = KeyboardRow()
        val row3 = KeyboardRow()
        val order = KeyboardButton(orderT)
        row1.add(order)
        val contactUs=KeyboardButton(contactUsT)
        row2.add(contactUs)
        val settings=KeyboardButton(settingsT)
        row2.add(settings)
        val location=KeyboardButton(locationT)
        row3.add(location)

        board.selective = true
        board.resizeKeyboard = true
        board.keyboard = listOf(row1,row2,row3)
        board.resizeKeyboard = true
        board.selective = true
        return board
    }






    fun categoryKeyboard(categoryList:MutableList<String>,messageSourceService:MessageSourceService):ReplyKeyboard{
        val first = KeyboardRow()
        val back = KeyboardButton(messageSourceService.getMessage(LocalizationTextKey.BACK_BUTTON))
        first.add(back)
        val pannier = KeyboardButton(messageSourceService.getMessage(LocalizationTextKey.PANNIER_BUTTON))
        first.add(pannier)

        val line= arrayListOf(first)


        for(i in categoryList ){


            if(categoryList.indexOf(i) == categoryList.size-1&&categoryList.indexOf(i)%2 == 0){
                val row = KeyboardRow()
                row.add(KeyboardButton(i))
                line.add(row)
            } else if(categoryList.indexOf(i)%2 == 0) {
                val row = KeyboardRow()
                row.add(KeyboardButton(i))
               row.add(KeyboardButton(categoryList[categoryList.indexOf(i)+1]))
                line.add(row)

            }else if (categoryList.indexOf(i)==categoryList.size-1 ){
                break
           }
        }

        board.selective = true
        board.resizeKeyboard = true
        board.keyboard = line
        board.resizeKeyboard = true
        board.selective = true
        return board


    }



//    fun FoodCategoryKeyboard():ReplyKeyboard{
//
//    }
//



}