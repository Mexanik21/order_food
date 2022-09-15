package com.example.order_food.response

data
class ResponseObj<Any>(
    var message:String,
    var httpStatus:Int,
    var success:Boolean,
    var obj: Any? = null
)