package com.example.order_food.Entity

import javax.persistence.Entity

@Entity
class File (
  var hashId:String?,
  var mimeType:String,
  var path:String?
        ):BaseEntity()