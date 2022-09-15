package com.example.order_food

import com.example.order_food.repository.BaseRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@EnableWebMvc
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl::class)
@EnableFeignClients
private class OrderFoodApplication

fun main(args: Array<String>) {
    runApplication<OrderFoodApplication>(*args)
}
