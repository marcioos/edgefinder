package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Money

class ArbitragePlanner {
    fun createPlans(
        bankroll: Money,
        opportunities: Iterable<ArbitrageOpportunity>,
    ): List<ArbitragePlan> =
        opportunities.map {
            ArbitragePlan.forBankroll(bankroll, it)
        }
}
