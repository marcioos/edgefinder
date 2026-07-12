package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.outcome.domain.MarketType
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
        val competitor: String,
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
            val outcome = opportunity.selections.first().outcome
            val event =
                outcome.event.run {
                    EventDto(
                        id = id,
                        home = home.name,
                        away = away.name,
                        startTime = startTime,
                    )
                }
            return ArbitrageOpportunityDto(
                event = event,
                market = outcome.market,
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
