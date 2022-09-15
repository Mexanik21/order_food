package com.example.order_food.security.filters

import com.example.order_food.service.impl.AuthService
import com.example.order_food.service.impl.JwtService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class AuthenticationJwtTokenFilter(
    private val authService: AuthService,
    private val jwtService: JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer")) {
            if (jwtService.validationToken(token)) {
                val username = jwtService.getUsername(token)
                val userDetails: UserDetails = authService.loadUserByUsername(username)
                if (!Objects.isNull(userDetails.username)) {
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}