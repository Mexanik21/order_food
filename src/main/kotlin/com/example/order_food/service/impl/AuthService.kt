package com.example.order_food.service.impl

import com.example.order_food.Entity.User
import com.example.order_food.dtos.JwtDto
import com.example.order_food.dtos.LoginUser
import com.example.order_food.exceptions.UniversalExceptions
import com.example.order_food.repository.UserRepository
import com.example.order_food.response.ResponseObj
import com.example.order_food.security.MyPasswordEncoder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service



@Service
class AuthService(
    private val myPasswordEncoder: MyPasswordEncoder,
    private val jwtService: JwtService,
    private val userRepository: UserRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val byUserName: User? = userRepository.findByUsername(username)
        if (byUserName == null){
            throw UniversalExceptions("User Not Found", HttpStatus.NOT_FOUND)
        }
        else{
            return byUserName
        }
    }

    fun login(loginUser: LoginUser): ResponseEntity<*>? {
        val byUserName = userRepository.findByUsername(loginUser.username)
        return if (byUserName == null) {
            ResponseEntity.status(404).body<Any>(ResponseObj(
                "User not found",
                404,
                false,
                null
            ))
        } else{
            val matches = myPasswordEncoder.passwordEncoder()!!.matches(loginUser.password, byUserName.password)
            if (!matches) {
                return ResponseEntity.status(404).body<Any>(ResponseObj(
                    "User or password wrong",
                    404,
                    false,
                    null
                ))
            }else{
                val jwtToken = jwtService.createJWTToken(byUserName)
                println(jwtToken)
                return ResponseEntity.status(200).body<Any>(
                    ResponseObj(
                        "Success",
                        200,
                        true,
                        JwtDto("Bearer $jwtToken","Bearer")
                    )
                )
            }
        }



    }
}
