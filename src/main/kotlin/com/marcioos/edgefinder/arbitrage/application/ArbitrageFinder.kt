package com.marcioos.edgefinder.arbitrage.application

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageCalculator
import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlan
import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlanner
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.odds.infrastructure.OddsRepository
import org.springframework.stereotype.Service

@Service
class ArbitrageFinder(
    private val oddsRepository: OddsRepository,
    private val arbitrageCalculator: ArbitrageCalculator,
    private val arbitragePlanner: ArbitragePlanner,
) {
    fun findOpportunities(): List<ArbitrageOpportunity> {
        val odds = oddsRepository.findCurrentOdds()

        return arbitrageCalculator.calculateOpportunities(odds)
    }

    fun findPlans(bankroll: Money): List<ArbitragePlan> {
        val opportunities = findOpportunities()

        return arbitragePlanner.createPlans(bankroll, opportunities)
    }
}
