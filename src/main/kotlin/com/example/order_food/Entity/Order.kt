package com.example.order_food.Entity

import com.example.order_food.enums.OrderStatus
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity(name = "buyurtma")
class Order(
    @ManyToOne
    var user: User,
    @ManyToOne
    var address: Address,
    var phoneNumber: String,
    @Enumerated(EnumType.STRING) var status: OrderStatus

) : BaseEntity()