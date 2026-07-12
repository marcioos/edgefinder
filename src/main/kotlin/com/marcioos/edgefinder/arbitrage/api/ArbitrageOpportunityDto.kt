package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.OutcomeSide
import java.time.Instant
import java.util.UUID

data class ArbitrageOpportunityDto(
    val event: EventDto,
    val market: MarketType,
    val roi: String,
    val selections: List<SelectionDto>,
) {
    data class SelectionDto(
        val sportsbook: String,
        val side: OutcomeSide,
        val decimalOdds: String,
        val americanOdds: Int,
    )

    data class EventDto(
        val id: UUID,
        val home: String,
        val away: String,
        val startTime: Instant,
    )

    companion object {
        fun from(opportunity: ArbitrageOpportunity): ArbitrageOpportunityDto {
            val market =
                opportunity.selections
                    .first()
                    .outcome.market
            val event =
                opportunity.selections
                    .first()
                    .outcome.event
            val eventDto =
                EventDto(
                    id = event.id,
                    home = event.home.name,
                    away = event.away.name,
                    startTime = event.startTime,
                )
            return ArbitrageOpportunityDto(
                event = eventDto,
                market = market,
                roi = opportunity.roi.value.toPlainString(),
                selections =
                    opportunity.selections.map {
                        SelectionDto(
                            sportsbook = it.sportsbook.name,
                            side = it.outcome.side,
                            decimalOdds = it.decimalOdds.value.toPlainString(),
                            americanOdds = it.decimalOdds.toAmerican().value,
                        )
                    },
            )
        }
    }
}
