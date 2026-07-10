package com.marcioos.edgefinder.common.api

import com.marcioos.edgefinder.common.api.dto.MoneyDto
import com.marcioos.edgefinder.common.domain.Currency
import com.marcioos.edgefinder.common.domain.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoneyDtoTest {
    @Test
    fun `should map money to dto`() {
        val money =
            Money(
                BigDecimal("1234.56"),
                Currency.USD,
            )

        val dto = MoneyDto.from(money)

        assertThat(dto.amount)
            .isEqualByComparingTo("1234.56")

        assertThat(dto.currency)
            .isEqualTo(Currency.USD)
    }

    @Test
    fun `should map dto to money`() {
        val dto =
            MoneyDto(
                amount = BigDecimal("987.65"),
                currency = Currency.BRL,
            )

        val money = dto.toMoney()

        assertThat(money).isEqualTo(
            Money(
                BigDecimal("987.65"),
                Currency.BRL,
            ),
        )
    }

    @Test
    fun `should preserve amount and currency through round trip`() {
        val original =
            Money(
                BigDecimal("42.10"),
                Currency.EUR,
            )

        val reconstructed =
            MoneyDto
                .from(original)
                .toMoney()

        assertThat(reconstructed)
            .isEqualTo(original)
    }
}
