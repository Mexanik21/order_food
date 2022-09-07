package com.example.order_food.dtos


data class SessionDto(

    private var accessToken: String? = null,
    val refreshToken: String? = null,
    val tokenType: String? = null,
    val refreshTokenExpire: Long? = null,
    val issuedAt: Long? = null,
    val expiresIn: Long? = null
) {
}