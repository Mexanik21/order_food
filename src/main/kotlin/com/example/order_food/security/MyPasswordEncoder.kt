package com.example.order_food.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class MyPasswordEncoder {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(4)
    }
}