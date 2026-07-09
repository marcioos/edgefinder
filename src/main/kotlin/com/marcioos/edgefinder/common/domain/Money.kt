package com.marcioos.edgefinder.common.domain

import com.marcioos.edgefinder.odds.domain.DecimalOdds
import java.math.BigDecimal

enum class Currency {
    USD,
}

data class Money(
    val value: BigDecimal,
    val currency: Currency = Currency.USD,
) : Comparable<Money> {
    constructor(value: String) : this(BigDecimal(value))

    operator fun plus(other: Money): Money = Money(value.add(other.value))

    operator fun times(ratio: Ratio): Money = Money(value.multiply(ratio.value))

    operator fun times(decimalOdds: DecimalOdds) = Money(value.multiply(decimalOdds.value))

    override fun compareTo(other: Money): Int = value.compareTo(other.value)
}
