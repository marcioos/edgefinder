package com.marcioos.edgefinder.common.domain

import com.marcioos.edgefinder.odds.domain.DecimalOdds
import java.math.BigDecimal

enum class Currency {
    BRL,
    EUR,
    USD,
}

data class Money(
    val amount: BigDecimal,
    val currency: Currency = Currency.USD,
) : Comparable<Money> {
    constructor(value: String) : this(BigDecimal(value))

    operator fun plus(other: Money): Money = Money(amount.add(other.amount))

    operator fun times(ratio: Ratio): Money = Money(amount.multiply(ratio.value))

    operator fun times(decimalOdds: DecimalOdds) = Money(amount.multiply(decimalOdds.value))

    override fun compareTo(other: Money): Int = amount.compareTo(other.amount)
}
