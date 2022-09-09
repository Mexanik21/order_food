package com.example.order_food

import com.example.order_food.Entity.OrderItem
import org.telegram.telegrambots.meta.api.objects.Update

fun Update.getTelegramId() = this.run {
    when {
        hasInlineQuery() -> inlineQuery.from.id
        hasChosenInlineQuery() -> chosenInlineQuery.from.id
        hasCallbackQuery() -> callbackQuery.from.id
        hasPreCheckoutQuery() -> preCheckoutQuery.from.id
        hasMyChatMember() -> myChatMember.from.id
        else -> message.from.id
    }
}


//fun pannierBody(list:List<OrderItem>){
//
//}

 fun pannierBody(orderItemList: MutableList<OrderItem>): String {
    val pannierBody = StringBuilder()
    var totalPrice = 0f
    var price = 0f

    for (item in orderItemList) {
        price=(item.count*item.food.price).toFloat()
        pannierBody.append(item.food.name).append(":\n")
        pannierBody.append(item.count).append(" x ").append(item.food.price).append(" = ").append(price).append("\n")

        totalPrice+=price
        price = 0f
    }


        pannierBody.append("\n")

    pannierBody.append("\n\uD83D\uDE9BYetkaib berish:  ").append("0\n")
    pannierBody.append("\uD83D\uDCB8Umumiy:  ").append(totalPrice + 0)
    return pannierBody.toString()
}
