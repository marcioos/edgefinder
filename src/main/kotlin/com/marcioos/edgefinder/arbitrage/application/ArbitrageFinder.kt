package com.marcioos.edgefinder.arbitrage.application

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageCalculator
import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.odds.application.OddsRepository

class ArbitrageFinder(
    private val oddsRepository: OddsRepository,
    private val arbitrageCalculator: ArbitrageCalculator,
) {
    fun findAll(): List<ArbitrageOpportunity> {
        val odds = oddsRepository.findCurrentOdds()

        return arbitrageCalculator.calculateOpportunities(odds)
    }
}
