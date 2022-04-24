package com.demo.sample

import org.springframework.stereotype.Service

@Service
class SampleWebService(
    private val sampleService: SampleService,
) {
    fun findAll(): List<SampleDto> {
        return sampleService.findAll()
            .map {
                SampleDto(
                    id = it.id,
                    name = it.name
                )
            }
    }

    fun findAllByName(name: String): List<SampleDto> {
        return sampleService.findAllByName(name = name)
            .map {
                SampleDto(
                    id = it.id,
                    name = it.name
                )
            }
    }

    fun save(sampleForm: SampleForm): SampleDto {
        return sampleService.save(
            Sample(
                name = sampleForm.name
            )
        ).let {
            SampleDto(
                id = it.id,
                name = it.name
            )
        }
    }
}
