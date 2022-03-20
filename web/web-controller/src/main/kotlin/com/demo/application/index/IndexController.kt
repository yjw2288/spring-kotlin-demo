package com.demo.application.index

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class IndexController {
    @GetMapping
    fun index(): String {
        return "index"
    }
}
