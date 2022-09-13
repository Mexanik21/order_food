package com.example.order_food.controllers

import com.example.order_food.dtos.Welcome
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(value = "getLocationInfo", url = "https://geocode-maps.yandex.ru/1.x/")
interface MyFeignClient {
    @GetMapping("?format=json&apikey=d0133d2d-b472-4cb5-af93-552102be95ef")
    fun getLocationInfo(
        @RequestParam("geocode") r:String,
        @RequestParam("results") a:String
    ): Welcome
}