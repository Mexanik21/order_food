package com.example.order_food.Entity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Category(
    var name: String,
    @ManyToOne
    @JoinColumn(name = "parent_id")
    var category: Category? = null
) : BaseEntity()