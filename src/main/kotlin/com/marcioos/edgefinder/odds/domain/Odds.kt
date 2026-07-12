package com.marcioos.edgefinder.odds.domain

import com.marcioos.edgefinder.common.domain.MathConstants.HUNDRED
import com.marcioos.edgefinder.common.domain.MathConstants.MINUS_HUNDRED
import com.marcioos.edgefinder.common.domain.MathConstants.TWO
import com.marcioos.edgefinder.common.domain.MathSettings.CALC_SCALE
import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.Outcome
import com.marcioos.edgefinder.sports.domain.Event
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.util.UUID
import kotlin.math.abs

data class Sportsbook(
    val id: UUID = UUID.randomUUID(),
    val name: String,
)

data class Odds(
    val id: UUID = UUID.randomUUID(),
    val sportsbook: Sportsbook,
    val outcome: Outcome,
    val decimalOdds: DecimalOdds,
    val updatedAt: Instant,
) {
    val impliedProbability
        get() = decimalOdds.impliedProbability()
}

data class MarketOdds(
    val event: Event,
    val market: MarketType,
    val odds: List<Odds>,
) {
    fun totalImpliedProbability(): Ratio = odds.map(Odds::impliedProbability).reduce(Ratio::plus)
}

@JvmInline
value class AmericanOdds(
    internal val value: Int,
) {
    init {
        require(value != 0)
        require(abs(value) >= 100) { "American odds must be <= -100 or >= +100" }
    }

    fun toDecimal(): DecimalOdds {
        val decimal =
            if (value > 0) {
                BigDecimal.ONE.add(BigDecimal(value).divide(HUNDRED, CALC_SCALE, RoundingMode.HALF_UP))
            } else {
                BigDecimal.ONE.add((HUNDRED.divide(BigDecimal(abs(value)), CALC_SCALE, RoundingMode.HALF_UP)))
            }
        return DecimalOdds(decimal)
    }
}

@JvmInline
value class DecimalOdds(
    internal val value: BigDecimal,
) : Comparable<DecimalOdds> {
    constructor(string: String) : this(BigDecimal(string))

    init {
        require(value > BigDecimal.ONE) { "Decimal odds must be > 1" }
    }

    fun impliedProbability(): Ratio =
        Ratio(
            BigDecimal.ONE.divide(
                value,
                CALC_SCALE,
                RoundingMode.HALF_UP,
            ),
        )

    fun toAmerican(): AmericanOdds {
        val american =
            if (value >= TWO) {
                value.subtract(BigDecimal.ONE).multiply(HUNDRED)
            } else {
                MINUS_HUNDRED.divide(value.subtract(BigDecimal.ONE), CALC_SCALE, RoundingMode.HALF_UP)
            }
        return AmericanOdds(american.setScale(0, RoundingMode.HALF_UP).toInt())
    }

    override fun compareTo(other: DecimalOdds): Int = this.value.compareTo(other.value)
}
