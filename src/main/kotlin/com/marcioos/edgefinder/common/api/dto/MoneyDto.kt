package com.marcioos.edgefinder.common.api.dto

import com.marcioos.edgefinder.common.domain.Currency
import com.marcioos.edgefinder.common.domain.Money
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class MoneyDto(
    @field:NotNull
    @field:DecimalMin(value = "0.01", inclusive = true)
    val amount: BigDecimal?,
    @field:NotNull
    val currency: Currency?,
) {
    fun toMoney(): Money = Money(amount!!, currency!!)

    companion object {
        fun from(money: Money): MoneyDto = MoneyDto(money.amount, money.currency)
    }
}
