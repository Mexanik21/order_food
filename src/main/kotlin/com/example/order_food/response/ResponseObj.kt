package com.example.order_food.response

class ResponseObj<T> {
    var message = "Ok"
    var httpStatus = 200
    var success = true
    var obj: Any? = null
}