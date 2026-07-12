package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArbitrageOpportunityTest {
    @Test
    fun `should calculate total implied probability`() {
        val marketOdds =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val opportunity = ArbitrageOpportunity.create(marketOdds)

        assertThat(opportunity.totalImpliedProbability)
            .isEqualTo(marketOdds.totalImpliedProbability())
    }

    @Test
    fun `roi should equal guaranteed profit divided by bankroll`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds("2.20", "2.15"),
            )

        val bankroll = Money("1000")

        val plan = ArbitragePlan.forBankroll(bankroll, opportunity)

        val expectedProfit = bankroll * opportunity.roi

        assertThat(plan.profit).isEqualTo(expectedProfit)
    }

    @Test
    fun `should keep all selected odds`() {
        val marketOdds =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val opportunity = ArbitrageOpportunity.create(marketOdds)

        assertThat(opportunity.selections)
            .containsExactlyInAnyOrderElementsOf(marketOdds.odds)
    }

    @Test
    fun `should allocate more stake to the more likely outcome`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val plan =
            ArbitragePlan.forBankroll(
                Money("1000"),
                opportunity,
            )

        val largerStake = plan.stakeAllocations.maxBy { it.stake }

        assertThat(largerStake.odds.decimalOdds.value).isEqualByComparingTo("2.15")
    }
}
