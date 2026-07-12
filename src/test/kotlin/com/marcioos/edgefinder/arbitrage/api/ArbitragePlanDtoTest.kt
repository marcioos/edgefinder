package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlan
import com.marcioos.edgefinder.bet.domain.StakeAllocation
import com.marcioos.edgefinder.common.api.dto.MoneyDto
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArbitragePlanDtoTest {
    @Test
    fun `should map arbitrage plan to dto`() {
        val opportunity =
            ArbitrageOpportunity.create(
                Fixtures.moneylineMarketOdds(
                    decimalOdds1 = "2.20",
                    decimalOdds2 = "2.15",
                ),
            )

        val plan =
            ArbitragePlan.forBankroll(
                Money("1000"),
                opportunity,
            )

        val dto = ArbitragePlanDto.from(plan)

        assertThat(dto.opportunity)
            .isEqualTo(ArbitrageOpportunityDto.from(opportunity))

        assertThat(dto.profit)
            .isEqualTo(MoneyDto.from(plan.profit))

        assertThat(dto.stakeAllocations)
            .hasSize(plan.stakeAllocations.size)

        dto.stakeAllocations
            .zip(
                plan.stakeAllocations,
            ).forEach { (allocationDto, allocationDomain) -> assertAllocationDto(allocationDto, allocationDomain) }
    }
}

fun assertAllocationDto(
    dto: ArbitragePlanDto.StakeAllocationDto,
    allocation: StakeAllocation,
) {
    assertThat(dto.sportsbook).isEqualTo(allocation.odds.sportsbook.name)
    assertThat(dto.competitor).isEqualTo(allocation.odds.outcome.displayName)
    assertThat(dto.stake).isEqualTo(MoneyDto.from(allocation.stake))
    assertThat(dto.decimalOdds).isEqualTo(
        allocation.odds.decimalOdds.value
            .toPlainString(),
    )
    assertThat(dto.americanOdds).isEqualTo(
        allocation.odds.decimalOdds
            .toAmerican()
            .value,
    )
}
