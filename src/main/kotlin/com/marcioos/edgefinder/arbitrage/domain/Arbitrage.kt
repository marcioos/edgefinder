package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.bet.domain.StakeAllocation
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.odds.domain.MarketOdds
import com.marcioos.edgefinder.odds.domain.Odds
import java.util.UUID

data class ArbitrageOpportunity(
    val id: UUID,
    val selections: List<Odds>,
    val totalImpliedProbability: Ratio,
    val roi: Ratio,
) {
    init {
        require(totalImpliedProbability < Ratio.ONE) {
            "An arbitrage opportunity should have total implied probability < 1. It was $totalImpliedProbability"
        }
    }

    companion object {
        fun create(marketOdds: MarketOdds): ArbitrageOpportunity = create(marketOdds, marketOdds.totalImpliedProbability())

        internal fun create(
            marketOdds: MarketOdds,
            totalImpliedProbability: Ratio,
            id: UUID = UUID.randomUUID(),
        ): ArbitrageOpportunity {
            val roi = calculateRoi(totalImpliedProbability)
            val selections = marketOdds.odds

            return ArbitrageOpportunity(id, selections, totalImpliedProbability, roi)
        }

        private fun calculateRoi(totalImpliedProbability: Ratio): Ratio = (Ratio.ONE / totalImpliedProbability) - Ratio.ONE
    }
}

data class ArbitragePlan(
    val opportunity: ArbitrageOpportunity,
    val stakeAllocations: List<StakeAllocation>,
    val profit: Money,
) {
    companion object {
        fun forBankroll(
            bankroll: Money,
            opportunity: ArbitrageOpportunity,
        ): ArbitragePlan {
            val profit = opportunity.roi * bankroll
            val stakeAllocations =
                opportunity.selections.map {
                    val stake = (it.impliedProbability / opportunity.totalImpliedProbability) * bankroll
                    StakeAllocation(it, stake)
                }
            return ArbitragePlan(opportunity, stakeAllocations, profit)
        }
    }
}
