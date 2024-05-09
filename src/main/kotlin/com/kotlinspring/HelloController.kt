package com.kotlinspring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloController {

    @GetMapping
    fun helloWorld(): String = "This is the the Rest end point!"
}