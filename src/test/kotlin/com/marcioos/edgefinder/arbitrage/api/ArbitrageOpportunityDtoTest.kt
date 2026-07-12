package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArbitrageOpportunityDtoTest {
    @Test
    fun `should map arbitrage opportunity to dto`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val dto = ArbitrageOpportunityDto.from(opportunity)

        assertThat(dto.market)
            .isEqualTo(
                opportunity.selections
                    .first()
                    .outcome.market.type,
            )

        assertThat(dto.roi)
            .isEqualTo(opportunity.roi.value.toPlainString())

        val event =
            opportunity.selections
                .first()
                .outcome.market.event

        assertThat(dto.event).isEqualTo(
            ArbitrageOpportunityDto.EventDto(
                id = event.id,
                home = event.home.name,
                away = event.away.name,
                startTime = event.startTime,
            ),
        )

        assertThat(dto.selections).hasSize(2)

        val expectedSelections =
            opportunity.selections.associateBy { it.outcome.displayName }

        assertThat(dto.selections).hasSize(expectedSelections.size)

        dto.selections.forEach { dtoSelection ->
            val odds = expectedSelections[dtoSelection.competitor]!!

            assertThat(dtoSelection.sportsbook).isEqualTo(odds.sportsbook.name)
            assertThat(dtoSelection.decimalOdds).isEqualTo(odds.decimalOdds.value.toPlainString())
            assertThat(dtoSelection.americanOdds).isEqualTo(odds.decimalOdds.toAmerican().value)
        }
    }
}
