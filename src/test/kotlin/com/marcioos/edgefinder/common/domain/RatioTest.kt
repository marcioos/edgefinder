package com.marcioos.edgefinder.common.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class RatioTest {

    @Test
    fun `should create ratio with zero`() {
        val ratio = Ratio("0")

        assertThat(ratio).isEqualTo(Ratio("0"))
    }

    @Test
    fun `should create ratio with positive value`() {
        val ratio = Ratio("0.919661")

        assertThat(ratio).isEqualTo(Ratio("0.919661"))
    }

    @Test
    fun `should reject negative ratio`() {
        assertThatThrownBy {
            Ratio("-0.0001")
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Ratio value should be >= 0. It was -0.0001.")
    }

    @Test
    fun `should format zero as percentage`() {
        assertThat(Ratio("0").asPercentageString())
            .isEqualTo("0.00%")
    }

    @Test
    fun `should format one as one hundred percent`() {
        assertThat(Ratio.ONE.asPercentageString())
            .isEqualTo("100.00%")
    }

    @Test
    fun `should format arbitrary percentage`() {
        assertThat(Ratio("0.0873563219").asPercentageString())
            .isEqualTo("8.74%")
    }

    @Test
    fun `should round percentage to two decimal places`() {
        assertThat(Ratio("0.123456").asPercentageString())
            .isEqualTo("12.35%")
    }

    @Test
    fun `should preserve trailing zeros in formatted percentage`() {
        assertThat(Ratio("0.5").asPercentageString())
            .isEqualTo("50.00%")
    }
}