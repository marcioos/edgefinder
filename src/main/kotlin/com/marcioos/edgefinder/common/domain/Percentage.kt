package com.marcioos.edgefinder.common.domain

import com.marcioos.edgefinder.common.domain.MathConstants.HUNDRED
import java.math.BigDecimal

@JvmInline
value class Percentage(val value: BigDecimal) {

    init {
        require(value >= BigDecimal.ZERO && value <= HUNDRED) { "Percentage value should be >= 0 and <= 100" }
    }
}
