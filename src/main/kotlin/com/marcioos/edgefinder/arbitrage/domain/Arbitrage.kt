package com.marcioos.edgefinder.arbitrage.domain

import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.common.domain.Percentage
import com.marcioos.edgefinder.odds.domain.Odds
import java.util.*

data class ArbitrageOpportunity(
    val id: UUID,
    val selections: List<Odds>,
    val roi: Percentage,
    val guaranteedProfit: Money
)
