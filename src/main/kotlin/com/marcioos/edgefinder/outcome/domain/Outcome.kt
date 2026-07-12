package com.marcioos.edgefinder.outcome.domain

import com.marcioos.edgefinder.sports.domain.Event
import java.util.UUID

enum class MarketType {
    MONEYLINE,
}

enum class OutcomeSide {
    HOME,
    AWAY,
}

sealed interface Outcome {
    val id: UUID
    val market: MarketType
    val event: Event
    val side: OutcomeSide
}

data class MoneylineOutcome(
    override val id: UUID = UUID.randomUUID(),
    override val market: MarketType = MarketType.MONEYLINE,
    override val event: Event,
    override val side: OutcomeSide,
) : Outcome
