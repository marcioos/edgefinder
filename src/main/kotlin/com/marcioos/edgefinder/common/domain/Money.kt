package com.marcioos.edgefinder.common.domain

import java.math.BigDecimal

enum class Currency {
    USD
}

data class Money(
    val value: BigDecimal,
    val currency: Currency = Currency.USD
)