package com.example.order_food.service.impl

import com.example.order_food.Entity.User
import com.example.order_food.dtos.UserDto
import com.example.order_food.mapper.UserMapper
import com.example.order_food.repository.UserRepository
import com.example.order_food.util.SecretKeys
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class JwtService(
    private val userRepository: UserRepository,

) {



    fun createJWTToken(user: User): String {
        val userDto = UserDto.toDto(user)
        val claims: Claims = Jwts.claims().setSubject(user.username)
        claims.put("user", userDto)


        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(java.util.Date(java.lang.System.currentTimeMillis()))
            .setExpiration(java.util.Date(SecretKeys.accessTokenDate))
            .signWith(SignatureAlgorithm.HS512, SecretKeys.secretWord).compact()


    }

    fun validationToken(tkn: String): Boolean {
        var token = tkn
        return try {
            token = tkn.substring(7)
            Jwts.parser().setSigningKey(SecretKeys.secretWord).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsername(token: String): String {
        val body: Claims =
            Jwts.parser().setSigningKey(SecretKeys.secretWord).parseClaimsJws(token.substring(7)).body
        return body.subject
    }
}