package com.example.order_food.Entity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne


@Entity(name = "buyurtmalar")
class OrderItem (
        @ManyToOne
        var order: Order,
        @OneToOne
        var food: Food,
        var count:Int

        ):BaseEntity()