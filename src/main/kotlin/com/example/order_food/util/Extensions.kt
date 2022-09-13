package com.example.order_food

import com.example.order_food.Entity.OrderItem
import com.example.order_food.Entity.User
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

fun orderInfo(user: User,orderItemList: MutableList<OrderItem>,address:String):String{
    val orderInfo=StringBuilder()
    orderInfo.append("Buyurtma \n\n")
    orderInfo.append("Buyurtma beruvchi : ").append(user.fullName).append("\n\n")
    orderInfo.append("Telefon raqama: ").append(user.phoneNumber).append("\n\n")
    orderInfo.append("Manzil").append(address).append("\n\n")
    orderInfo.append(pannierBody(orderItemList))
    return orderInfo.toString()

}


//fun getLocationInfo(latitude:Double,longitude:Double) {
//
//        "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=d0133d2d-b472-4cb5-af93-552102be95ef&geocode=$latitude,$longitude&results=1"
//
//}


