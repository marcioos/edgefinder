package com.marcioos.edgefinder.outcome.domain

import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import java.util.UUID

enum class MarketType {
    MONEYLINE,
}

sealed interface Market {
    val id: UUID
    val event: Event
    val type: MarketType
}

sealed interface Outcome {
    val id: UUID
    val market: Market
    val displayName: String
}

data class MoneylineMarket(
    override val event: Event,
    override val id: UUID = UUID.randomUUID(),
    override val type: MarketType = MarketType.MONEYLINE,
) : Market

data class MoneylineOutcome(
    override val market: MoneylineMarket,
    val competitor: Competitor,
    override val id: UUID = UUID.randomUUID(),
) : Outcome {
    override val displayName: String
        get() = competitor.name
}
