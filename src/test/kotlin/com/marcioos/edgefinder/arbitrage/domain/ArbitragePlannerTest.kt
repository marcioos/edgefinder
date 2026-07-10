package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArbitragePlannerTest {
    private val planner = ArbitragePlanner()

    @Test
    fun `should create plans for all opportunities`() {
        val bankroll = Money("1000")

        val firstOpportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val secondOpportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.30",
                    decimalOdds2 = "2.05",
                    market = Fixtures.secondMarket,
                ),
            )

        val plans =
            planner.createPlans(
                bankroll,
                listOf(firstOpportunity, secondOpportunity),
            )

        assertThat(plans).hasSize(2)

        assertThat(plans[0])
            .isEqualTo(ArbitragePlan.forBankroll(bankroll, firstOpportunity))

        assertThat(plans[1])
            .isEqualTo(ArbitragePlan.forBankroll(bankroll, secondOpportunity))
    }

    @Test
    fun `should return empty list when there are no opportunities`() {
        val plans =
            planner.createPlans(
                Money("1000"),
                emptyList(),
            )

        assertThat(plans).isEmpty()
    }
}
