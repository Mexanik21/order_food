package com.example.order_food.service

import org.telegram.telegrambots.meta.api.objects.Update

interface Authenticate {
    fun authenticate(update: Update)
}