package com.example.order_food.service.impl

import com.example.order_food.Entity.Address
import com.example.order_food.dtos.AddressCreateDto
import com.example.order_food.dtos.AddressResponseDto
import com.example.order_food.dtos.AddressUpdateDto
import com.example.order_food.repository.AddressRepository
import com.example.order_food.repository.UserRepository
import com.example.order_food.service.AddressService
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository
): AddressService {
    override fun create(dto: AddressCreateDto) {
       dto.apply {
           addressRepository.save(Address(
               userRepository.findById(userId).orElseThrow{Exception("user not found")},
               address,
               comment,
               latitide,
               longitude))
       }
    }

    override fun getOne(id: Long) = AddressResponseDto.toDto(
        addressRepository.findById(id).orElseThrow{Exception("Address not found $id")}
    )

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