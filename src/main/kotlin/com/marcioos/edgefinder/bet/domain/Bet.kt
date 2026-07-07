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
    val id: UUID,
    val odds: Odds,
    val stake: Money,
    val status: BetStatus
)
