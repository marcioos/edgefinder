package com.marcioos.edgefinder.odds.domain

import com.marcioos.edgefinder.common.domain.MathConstants.HUNDRED
import com.marcioos.edgefinder.common.domain.MathConstants.MINUS_HUNDRED
import com.marcioos.edgefinder.common.domain.MathConstants.TWO
import com.marcioos.edgefinder.common.domain.MathSettings.CALC_SCALE
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
        require(abs(value) >= 100) { "American odds must be <= -100 or >= +100" }
    }

    fun toDecimal(): DecimalOdds {
        val decimal = if (value > 0) {
            BigDecimal.ONE.add(BigDecimal(value).divide(HUNDRED, CALC_SCALE, RoundingMode.HALF_UP))
        } else {
            BigDecimal.ONE.add((HUNDRED.divide(BigDecimal(abs(value)), CALC_SCALE, RoundingMode.HALF_UP)))
        }
        return DecimalOdds(decimal)
    }
}

@JvmInline
value class DecimalOdds(val value: BigDecimal) {

    init {
        require(value > BigDecimal.ONE) { "Decimal odds must be > 1" }
    }

    fun impliedProbability(): Percentage = Percentage((BigDecimal.ONE.divide(value))
        .multiply(HUNDRED)
        .setScale(CALC_SCALE, RoundingMode.HALF_UP))

    fun toAmerican(): AmericanOdds {
        val american = if (value >= TWO) {
            value.subtract(BigDecimal.ONE).multiply(HUNDRED)
        } else {
            MINUS_HUNDRED.divide(value.subtract(BigDecimal.ONE), CALC_SCALE, RoundingMode.HALF_UP)
        }
        return AmericanOdds(american.setScale(0, RoundingMode.HALF_UP).toInt())
    }
}