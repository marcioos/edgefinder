package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ArbitragePlanTest {
    @Test
    fun `should calculate profit`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val bankroll = Money("1000")

        val plan = ArbitragePlan.forBankroll(bankroll, opportunity)

        assertThat(plan.profit.amount).isCloseTo(BigDecimal("87.358"), within(BigDecimal("0.01")))
    }

    @Test
    fun `should allocate entire bankroll`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val bankroll = Money("1000")

        val plan = ArbitragePlan.forBankroll(bankroll, opportunity)

        val allocated =
            plan.stakeAllocations
                .map { it.stake }
                .reduce(Money::plus)

        assertThat(allocated).isEqualByComparingTo(bankroll)
    }

    @Test
    fun `should generate one stake allocation per selection`() {
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

        assertThat(plan.stakeAllocations)
            .hasSize(opportunity.selections.size)
    }

    @Test
    fun `should produce equal payouts regardless of outcome`() {
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

        val payouts =
            plan.stakeAllocations.map {
                it.stake * it.odds.decimalOdds
            }

        assertThat(payouts[0].amount)
            .isCloseTo(
                payouts[1].amount,
                within(BigDecimal("0.01")),
            )
    }
}
