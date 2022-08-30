package com.example.order_food.Entity

import com.example.order_food.enums.Language
import com.example.order_food.enums.Role
import com.example.order_food.enums.Step
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "users")
class User(
    @Column(length = 48, unique = true) var telegramId: String,
    @Enumerated(EnumType.STRING) var role: Role = Role.USER,
    @Enumerated(EnumType.STRING) var step: Step = Step.START,
    @Enumerated(EnumType.STRING) var  lang: Language = Language.UZ,
    @Column(length = 24, unique = true) var username: String? = null,
    @Column(length = 128) var fullName: String? = null,
    @Column(length = 16) var phoneNumber: String? = null,


    ) : BaseEntity()