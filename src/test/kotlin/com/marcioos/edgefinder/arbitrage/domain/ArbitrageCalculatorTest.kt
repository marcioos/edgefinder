package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.fixtures.Fixtures
import com.marcioos.edgefinder.fixtures.awayOdds
import com.marcioos.edgefinder.fixtures.homeOdds
import com.marcioos.edgefinder.odds.domain.DecimalOdds
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class ArbitrageCalculatorTest {
    private val calculator = ArbitrageCalculator()

    @Test
    fun `should return empty list when no arbitrage opportunities exist`() {
        val marketOdds =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "1.91",
                decimalOdds2 = "1.91",
            )

        val opportunities = calculator.calculateOpportunities(marketOdds.odds)

        assertThat(opportunities).isEmpty()
    }

    @Test
    fun `should find arbitrage opportunity`() {
        val marketOdds =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val opportunities = calculator.calculateOpportunities(marketOdds.odds)

        assertThat(opportunities).hasSize(1)

        val opportunity = opportunities.single()

        assertThat(opportunity.selections)
            .containsExactlyInAnyOrderElementsOf(marketOdds.odds)

        assertThat(opportunity.roi)
            .isGreaterThan(Ratio.ZERO)
    }

    @Test
    fun `should select highest odds for each outcome`() {
        val marketOdds =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val betterHomeOdds =
            marketOdds.homeOdds.copy(
                decimalOdds = DecimalOdds("2.30"),
            )

        val worseHomeOdds =
            marketOdds.homeOdds.copy(
                decimalOdds = DecimalOdds("2.10"),
            )

        val opportunities =
            calculator.calculateOpportunities(
                listOf(
                    betterHomeOdds,
                    worseHomeOdds,
                    marketOdds.awayOdds,
                ),
            )

        val opportunity = opportunities.single()

        assertThat(opportunity.selections)
            .contains(betterHomeOdds)
            .doesNotContain(worseHomeOdds)
    }

    @Test
    fun `should calculate one opportunity per market`() {
        val firstMarket =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val secondMarket =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.30",
                decimalOdds2 = "2.05",
                market = Fixtures.secondMarket,
            )

        val opportunities =
            calculator.calculateOpportunities(
                firstMarket.odds + secondMarket.odds,
            )

        assertThat(opportunities).hasSize(2)
    }

    @Test
    fun `should ignore non arbitrage markets`() {
        val arbitrage =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            )

        val nonArbitrage =
            Fixtures.moneylineMarketOdds(
                decimalOdds1 = "1.91",
                decimalOdds2 = "1.91",
                market = Fixtures.secondMarket,
            )

        val opportunities =
            calculator.calculateOpportunities(
                arbitrage.odds + nonArbitrage.odds,
            )

        assertThat(opportunities).hasSize(1)
    }

    @Test
    fun `should return empty list for empty odds`() {
        assertThat(calculator.calculateOpportunities(emptyList())).isEmpty()
    }
}
