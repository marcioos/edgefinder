package com.marcioos.edgefinder.odds.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class AmericanOddsTest {
    @Test
    fun `should reject zero`() {
        assertThrows<IllegalArgumentException> {
            AmericanOdds(0)
        }
    }

    @Test
    fun `should reject values between -99 and 99`() {
        for (i in -99..99) {
            if (i == 0) {
                continue
            }

            assertThrows<IllegalArgumentException> {
                AmericanOdds(i)
            }
        }
    }

    @Test
    fun `should convert positive american odds to decimal`() {
        assertThat(AmericanOdds(100).toDecimal().value)
            .isEqualByComparingTo("2.0")

        assertThat(AmericanOdds(150).toDecimal().value)
            .isEqualByComparingTo("2.5")

        assertThat(AmericanOdds(200).toDecimal().value)
            .isEqualByComparingTo("3.0")
    }

    @Test
    fun `should convert negative american odds to decimal`() {
        assertThat(AmericanOdds(-200).toDecimal().value)
            .isEqualByComparingTo("1.5")

        assertThat(AmericanOdds(-400).toDecimal().value)
            .isEqualByComparingTo("1.25")
    }

    @Test
    fun `should convert minus one hundred ten to approximately one point nine zero nine`() {
        assertThat(AmericanOdds(-110).toDecimal().value)
            .isCloseTo(BigDecimal("1.909090909"), Offset.offset(BigDecimal("0.000000001")))
    }
}
