package com.example.order_food.mapper

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper(componentModel = "spring")
public interface UserMapper {

    fun fromUser(user: User?): UserDto

}
