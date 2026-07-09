package com.marcioos.edgefinder.common.domain

import com.marcioos.edgefinder.common.domain.MathSettings.CALC_SCALE
import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Ratio(internal val value: BigDecimal) : Comparable<Ratio> {
    constructor(string: String) : this(BigDecimal(string))

    companion object {
        val ZERO = Ratio(BigDecimal.ZERO)
        val ONE = Ratio(BigDecimal.ONE)
    }

    operator fun plus(other: Ratio): Ratio {
        return Ratio(value.add(other.value))
    }

    operator fun times(money: Money): Money = Money(value.multiply(money.value))

    operator fun div(other: Ratio) : Ratio = Ratio(value.divide(other.value, CALC_SCALE, RoundingMode.HALF_UP))

    operator fun minus(other: Ratio): Ratio {
        return Ratio(value.subtract(other.value))
    }

    override fun compareTo(other: Ratio): Int {
        return this.value.compareTo(other.value)
    }

    fun asPercentageString(): String = value
        .multiply(BigDecimal("100"))
        .setScale(2, RoundingMode.HALF_UP)
        .toPlainString() + "%"
}
