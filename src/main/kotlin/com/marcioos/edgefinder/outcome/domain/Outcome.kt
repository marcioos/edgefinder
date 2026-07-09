package com.marcioos.edgefinder.outcome.domain

import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import java.util.UUID

sealed interface Market {
    val id: UUID
    val event: Event
}

sealed interface Outcome {
    val id: UUID
    val market: Market
}

data class MoneylineMarket(
    override val event: Event,
    override val id: UUID = UUID.randomUUID(),
) : Market

data class MoneylineOutcome(
    override val market: MoneylineMarket,
    val competitor: Competitor,
    override val id: UUID = UUID.randomUUID(),
) : Outcome
