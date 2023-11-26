package com.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

data class Wallet(
    val money: Money
)

internal class MoneyTest {

    @Test
    fun `money serializer`() {
        val objectMapper = ObjectMapper()

        val walletJson = objectMapper.writeValueAsString(
            Wallet(Money(BigDecimal.ONE))
        ).also {
            println(it)
        }

        val deWallet = objectMapper.readValue(walletJson, Money::class.java)
        println(deWallet)
    }
}
