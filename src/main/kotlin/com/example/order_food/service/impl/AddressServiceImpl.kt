package com.example.order_food.service.impl

import com.example.order_food.Entity.Address
import com.example.order_food.controllers.MyFeignClient
import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.AddressResponseDto
import com.example.order_food.dtos.AddressUpdateDto
import com.example.order_food.dtos.Welcome
import com.example.order_food.repository.AddressRepository
import com.example.order_food.repository.UserRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.service.AddressService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository
): AddressService {
    override fun create(dto: AddressCreateDto): AddressResponseDto {
        val user = userRepository.findByIdAndDeletedIsFalse(dto.userId)

        val address = addressRepository.save(Address(user!!, dto.address, dto.comment, dto.latitide, dto.longitude))
           return AddressResponseDto.toDto(address)
    }

    override fun getOne(id: Long) = addressRepository.lastAfindByUserId(id)


    override fun getAll() = addressRepository.findAll().map{
        AddressResponseDto.toDto(it)
    }

    override fun update(id: Long, dto: AddressUpdateDto) {
        val add = addressRepository.findById(id).orElseThrow{Exception("Address not found $id")}
        dto.apply {
            address.let { add.address = it }
            comment.let { add.comment = it }
            longitude.let { add.longitude = it }
            latitide.let { add.latitide = it }
        }
        addressRepository.save(add)
    }

    override fun delete(id: Long) {
        addressRepository.deleteById(id)
    }


}