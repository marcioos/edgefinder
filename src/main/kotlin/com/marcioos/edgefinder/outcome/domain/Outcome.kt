package com.marcioos.edgefinder.outcome.domain

import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import java.util.UUID

enum class MarketType {
    MONEYLINE,
}

sealed interface Outcome {
    val id: UUID
    val market: MarketType
    val event: Event
    val displayName: String
}

data class MoneylineOutcome(
    override val id: UUID = UUID.randomUUID(),
    override val market: MarketType = MarketType.MONEYLINE,
    override val event: Event,
    val competitor: Competitor,
) : Outcome {
    override val displayName: String
        get() = competitor.name
}
