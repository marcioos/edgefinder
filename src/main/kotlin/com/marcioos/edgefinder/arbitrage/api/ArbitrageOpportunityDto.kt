package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.outcome.domain.MarketType
import java.time.ZonedDateTime
import java.util.UUID

data class ArbitrageOpportunityDto(
    val event: EventDto,
    val market: MarketType,
    val roi: String,
    val selections: List<SelectionDto>,
) {
    data class SelectionDto(
        val sportsbook: String,
        val competitor: String,
        val decimalOdds: String,
        val americanOdds: Int,
    )

    data class EventDto(
        val id: UUID,
        val home: String,
        val away: String,
        val startTime: ZonedDateTime,
    )

    companion object {
        fun from(opportunity: ArbitrageOpportunity): ArbitrageOpportunityDto {
            val market =
                opportunity.selections
                    .first()
                    .outcome.market
            val event =
                EventDto(
                    id = market.event.id,
                    home = market.event.home.name,
                    away = market.event.away.name,
                    startTime = market.event.startTime,
                )

            return ArbitrageOpportunityDto(
                event = event,
                market = market.type,
                roi = opportunity.roi.value.toPlainString(),
                selections =
                    opportunity.selections.map {
                        SelectionDto(
                            sportsbook = it.sportsbook.name,
                            competitor = it.outcome.displayName,
                            decimalOdds = it.decimalOdds.value.toPlainString(),
                            americanOdds = it.decimalOdds.toAmerican().value,
                        )
                    },
            )
        }
    }
}
