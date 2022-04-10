package com.demo.application.sample

import com.demo.sample.SampleDto
import com.demo.sample.SampleForm
import com.demo.sample.SampleWebService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleController(
    private val sampleWebService: SampleWebService
) {
    @GetMapping
    fun sample(): List<SampleDto> {
        return sampleWebService.findAll()
    }

    @GetMapping("/{name}")
    fun hangulTest(
        @PathVariable name: String,
        @RequestParam value: String
    ): String {
        return "$name | $value"
    }

    @GetMapping("/save")
    fun save(@RequestParam name: String): SampleDto {
        return sampleWebService.save(
            SampleForm(
                name = name
            )
        )
    }

    @GetMapping("/find")
    fun find(@RequestParam name: String): List<SampleDto> {
        return sampleWebService.findAllByName(name)
    }
}
