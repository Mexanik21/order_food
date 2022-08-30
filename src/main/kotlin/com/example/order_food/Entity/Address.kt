package com.example.order_food.Entity

import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Address(
    @ManyToOne
    var user: User,
    var address: String,
    var comment:String,
    var latitide:String,
    var longitude:String
): BaseEntity()