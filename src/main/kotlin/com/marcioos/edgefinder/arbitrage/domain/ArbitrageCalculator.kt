package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.odds.domain.MarketOdds
import com.marcioos.edgefinder.odds.domain.Odds
import com.marcioos.edgefinder.outcome.domain.Outcome

class ArbitrageCalculator {
    fun calculateOpportunities(odds: List<Odds>): List<ArbitrageOpportunity> {
        val oddsByMarket = getBestOddsPerOutcome(odds).values.groupBy { it.outcome.event to it.outcome.market }

        return oddsByMarket.mapNotNull { (key, odds) ->
            val (event, market) = key
            findOpportunity(MarketOdds(event, market, odds))
        }
    }

    private fun getBestOddsPerOutcome(odds: List<Odds>): Map<Outcome, Odds> =
        odds.groupBy(Odds::outcome).mapValues { (_, odds) -> odds.maxBy(Odds::decimalOdds) }

    private fun findOpportunity(marketOdds: MarketOdds): ArbitrageOpportunity? {
        val totalImpliedProbability = marketOdds.totalImpliedProbability()
        return if (totalImpliedProbability >= Ratio.ONE) {
            null
        } else {
            ArbitrageOpportunity.create(marketOdds)
        }
    }
}
