package com.demo.sample

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SampleService internal constructor(
    private val sampleRepository: SampleRepository
) {
    fun findAll(): List<Sample> = sampleRepository.findAll()
    fun save(sample: Sample): Sample = sampleRepository.save(sample)

    fun findAllByName(name: String): List<Sample> = sampleRepository.findAll(QSample.sample.name.eq(name)).toList()
}
