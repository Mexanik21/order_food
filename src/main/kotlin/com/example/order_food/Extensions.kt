package com.example.order_food

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
