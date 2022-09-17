package com.example.order_food

import com.example.order_food.Entity.Food
import com.example.order_food.Entity.OrderItem
import com.example.order_food.Entity.User
import com.example.order_food.enums.CallbackType
import com.example.order_food.repository.OrderItemRepository
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
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


fun pannierBody(orderItemList: MutableList<OrderItem>): String {
    val pannierBody = StringBuilder()
    var totalPrice = 0f
    var price = 0f

    for (item in orderItemList) {
        price = (item.count * item.food.price).toFloat()
        pannierBody.append(item.food.name).append(":\n")
        pannierBody.append(item.count).append(" x ").append(item.food.price).append(" = ").append(price).append("\n")

        totalPrice += price
        price = 0f
    }


    pannierBody.append("\n")

    pannierBody.append("\n\uD83D\uDE9BYetkaib berish:  ").append("0\n")
    pannierBody.append("\uD83D\uDCB8Umumiy:  ").append(totalPrice + 0)
    return pannierBody.toString()
}

fun orderInfo(user: User, orderItemList: MutableList<OrderItem>, address: String): String {
    val orderInfo = StringBuilder()
    orderInfo.append("Buyurtma \n\n")
    orderInfo.append("Buyurtma beruvchi : ").append(user.fullName).append("\n\n")
    orderInfo.append("Telefon raqama: ").append(user.phoneNumber).append("\n\n")
    orderInfo.append("Manzil").append(address).append("\n\n")
    orderInfo.append(pannierBody(orderItemList))
    return orderInfo.toString()

}

fun descriptionFood(food: Food):String{
 val description=StringBuilder()
    description.append("\n${food.name}\n")
    description.append("\nNarxi : ${food.price}\n\n")
    description.append(food.description)
    description.append("\n\n")
    return description.toString()

}


fun existsTextInCacheList(text: String, cacheList: MutableList<String>?): Boolean {

    if (cacheList != null) {
        for (i in cacheList) {
            if (text.equals(i)) {
                return true
            }
        }

        return false

    }

    return false
}

fun addOrderItems(name:String, orderItemRepository: OrderItemRepository){





}



