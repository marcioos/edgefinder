package com.marcioos.edgefinder.odds.domain

import com.marcioos.edgefinder.common.domain.Percentage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class DecimalOddsTest {

    @Test
    fun `should reject decimal odds less than or equal to one`() {
        assertThrows<IllegalArgumentException> {
            DecimalOdds(BigDecimal.ONE)
        }

        assertThrows<IllegalArgumentException> {
            DecimalOdds(BigDecimal("0.95"))
        }
    }

    @Test
    fun `should calculate implied probability`() {
        assertThat(
            DecimalOdds(BigDecimal("2.0")).impliedProbability().value
        ).isEqualByComparingTo(BigDecimal("50"))

        assertThat(
            DecimalOdds(BigDecimal("4.0")).impliedProbability().value
        ).isEqualByComparingTo(BigDecimal("25"))
    }

    @Test
    fun `should convert decimal odds greater than or equal to two to american`() {
        assertThat(
            DecimalOdds(BigDecimal("2.0"))
                .toAmerican()
                .value
        ).isEqualTo(100)

        assertThat(
            DecimalOdds(BigDecimal("2.5"))
                .toAmerican()
                .value
        ).isEqualTo(150)

        assertThat(
            DecimalOdds(BigDecimal("3.0"))
                .toAmerican()
                .value
        ).isEqualTo(200)
    }

    @Test
    fun `should convert decimal odds below two to american`() {
        assertThat(
            DecimalOdds(BigDecimal("1.5"))
                .toAmerican()
                .value
        ).isEqualTo(-200)

        assertThat(
            DecimalOdds(BigDecimal("1.25"))
                .toAmerican()
                .value
        ).isEqualTo(-400)
    }

    @Test
    fun `should round trip american to decimal and back`() {
        val values = listOf(
            100,
            120,
            150,
            200,
            -110,
            -125,
            -150,
            -200,
            -400
        )

        values.forEach {
            assertThat(
                AmericanOdds(it)
                    .toDecimal()
                    .toAmerican()
                    .value
            ).isEqualTo(it)
        }
    }
}