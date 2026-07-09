package com.marcioos.edgefinder.bet.domain

import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.odds.domain.Odds
import java.util.*

enum class BetStatus {
    PENDING,
    WON,
    LOST,
    CANCELLED
}

data class Bet(
    val odds: Odds,
    val stake: Money,
    val status: BetStatus,
    val id: UUID = UUID.randomUUID()
)

data class StakeAllocation(
    val odds: Odds,
    val stake: Money
)
