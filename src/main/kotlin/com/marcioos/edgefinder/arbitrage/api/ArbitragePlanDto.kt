package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlan
import com.marcioos.edgefinder.common.api.dto.MoneyDto

data class ArbitragePlanDto(
    val opportunity: ArbitrageOpportunityDto,
    val profit: MoneyDto,
    val stakeAllocations: List<StakeAllocationDto>,
) {
    data class StakeAllocationDto(
        val sportsbook: String,
        val competitor: String,
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
                            competitor = it.odds.outcome.displayName,
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
