package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlan
import com.marcioos.edgefinder.common.api.dto.MoneyDto
import com.marcioos.edgefinder.outcome.domain.OutcomeSide

data class ArbitragePlanDto(
    val opportunity: ArbitrageOpportunityDto,
    val profit: MoneyDto,
    val stakeAllocations: List<StakeAllocationDto>,
) {
    data class StakeAllocationDto(
        val sportsbook: String,
        val side: OutcomeSide,
        val stake: MoneyDto,
        val decimalOdds: String,
        val americanOdds: Int,
    )

    companion object {
        fun from(plan: ArbitragePlan): ArbitragePlanDto =
            ArbitragePlanDto(
                opportunity = ArbitrageOpportunityDto.from(plan.opportunity),
                profit = MoneyDto.from(plan.profit),
                stakeAllocations =
                    plan.stakeAllocations.map {
                        StakeAllocationDto(
                            sportsbook = it.odds.sportsbook.name,
                            side = it.odds.outcome.side,
                            stake = MoneyDto.from(it.stake),
                            decimalOdds =
                                it.odds.decimalOdds.value
                                    .toPlainString(),
                            americanOdds =
                                it.odds.decimalOdds
                                    .toAmerican()
                                    .value,
                        )
                    },
            )
    }
}
