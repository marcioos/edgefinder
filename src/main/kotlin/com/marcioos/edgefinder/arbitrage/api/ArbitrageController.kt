package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.application.ArbitrageFinder
import com.marcioos.edgefinder.common.api.dto.MoneyDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/arbitrage")
class ArbitrageController(
    private val finder: ArbitrageFinder,
) {
    @GetMapping("/opportunities")
    fun opportunities(): List<ArbitrageOpportunityDto> = finder.findOpportunities().map(ArbitrageOpportunityDto::from)

    @GetMapping("/plans")
    fun plans(
        @Valid @ModelAttribute bankroll: MoneyDto,
    ): List<ArbitragePlanDto> = finder.findPlans(bankroll.toMoney()).map(ArbitragePlanDto::from)
}
