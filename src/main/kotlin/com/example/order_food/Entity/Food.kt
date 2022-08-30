package com.example.order_food.Entity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class Food(
    var name: String,
    var price: Long,
    @ManyToOne
    var category: Category,
    var status: Boolean?=null,
    @OneToOne
    var file: File?=null

) : BaseEntity()