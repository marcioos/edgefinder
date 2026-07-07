package com.marcioos.edgefinder.odds.domain

import com.marcioos.edgefinder.common.domain.Percentage
import com.marcioos.edgefinder.outcome.domain.Outcome
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.util.*
import kotlin.math.abs

data class Sportsbook(
    val id: UUID,
    val name: String
)

data class Odds(
    val id: UUID,
    val sportsbook: Sportsbook,
    val outcome: Outcome,
    val decimalOdds: DecimalOdds,
    val updatedAt: Instant
)

@JvmInline
value class AmericanOdds(val value: Int) {

    init {
        require(value != 0)
        require(value >= 100 || value <= -100) { "American odds must be <= -100 or >= +100" }
    }

    fun toDecimal(): DecimalOdds {
        val decimal = if (value > 0) {
            BigDecimal.ONE + BigDecimal(value).divide(BigDecimal("100"))
        } else {
            BigDecimal.ONE + (BigDecimal("100").divide(BigDecimal(abs(value)), 10, RoundingMode.HALF_UP))
        }
        return DecimalOdds(Percentage(decimal))
    }
}

@JvmInline
value class DecimalOdds(val percentage: Percentage) {

    init {
        require(percentage.value > BigDecimal.ONE) { "Decimal odds must be > 1"}
    }

    fun impliedProbability(): BigDecimal = (BigDecimal.ONE.divide(percentage.value)).multiply(BigDecimal("100"))

    fun toAmerican(): AmericanOdds {
        val american = if (percentage.value >= BigDecimal("2")) {
            percentage.value.subtract(BigDecimal.ONE).multiply(BigDecimal("100"))
        } else {
            BigDecimal("-100").divide(percentage.value.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP)
        }
        return AmericanOdds(american.setScale(0, RoundingMode.HALF_UP).toInt())
    }
}