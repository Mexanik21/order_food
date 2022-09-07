package com.example.order_food.service.impl

import com.example.order_food.Entity.User
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
        val byUserName = userRepository.findByUsername(loginUser.userName)
        val responseObj: ResponseObj<Any> = ResponseObj()
        return if (byUserName == null) {
            responseObj.httpStatus = 404
            responseObj.message = "User not found"
            responseObj.success = false
            ResponseEntity.status(404).body<Any>(responseObj)
        } else{
            val matches = myPasswordEncoder.passwordEncoder()!!.matches(loginUser.password, byUserName.password)
            if (!matches) {
                responseObj.message = "User or password wrong"
                responseObj.httpStatus = 404
                return ResponseEntity.status(404).body<Any>(responseObj)
            }else{
                val jwtToken = jwtService.createJWTToken(byUserName)
                println(jwtToken)
                responseObj.obj = jwtToken
                return ResponseEntity.status(200).body<Any>(responseObj)
            }
        }



    }
}
