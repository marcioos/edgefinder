package com.marcioos.edgefinder.outcome.domain

import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import java.util.*

sealed interface Market {
    val id: UUID
    val event: Event
}

sealed interface Outcome {
    val id: UUID
    val market: Market
}

data class MoneylineMarket(
    override val id: UUID,
    override val event: Event
) : Market

data class MoneylineOutcome(
    override val id: UUID,
    override val market: MoneylineMarket,
    val competitor: Competitor
) : Outcome
