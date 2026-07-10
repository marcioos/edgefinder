package com.marcioos.edgefinder.arbitrage.api

import com.marcioos.edgefinder.arbitrage.application.ArbitrageFinder
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.fixtures.Fixtures
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@WebMvcTest(ArbitrageController::class)
@AutoConfigureMockMvc
class ArbitrageControllerIT {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var finder: ArbitrageFinder

    @Test
    fun `should return opportunities`() {
        val opportunity = Fixtures.arbitrageOpportunity()

        Mockito
            .`when`(finder.findOpportunities())
            .thenReturn(listOf(opportunity))

        val expected =
            objectMapper.writeValueAsString(
                listOf(ArbitrageOpportunityDto.from(opportunity)),
            )

        mockMvc
            .perform(get("/api/arbitrage/opportunities"))
            .andExpect(status().isOk())
            .andExpect(content().json(expected))
    }

    @Test
    fun `should return plans`() {
        val bankroll = Money("1000")
        val plan = Fixtures.arbitragePlan(bankroll = bankroll)

        Mockito
            .`when`(finder.findPlans(bankroll))
            .thenReturn(listOf(plan))

        val expected =
            objectMapper.writeValueAsString(
                listOf(ArbitragePlanDto.from(plan)),
            )

        mockMvc
            .perform(
                get("/api/arbitrage/plans")
                    .param("amount", bankroll.amount.toPlainString())
                    .param("currency", bankroll.currency.toString()),
            ).andExpect(status().isOk())
            .andExpect(content().json(expected))
    }

    @Test
    fun `should return validation error when amount is missing`() {
        mockMvc
            .perform(
                get("/api/arbitrage/plans")
                    .param("currency", "USD"),
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
    }

    @Test
    fun `should return validation error when currency is missing`() {
        mockMvc
            .perform(
                get("/api/arbitrage/plans")
                    .param("amount", "100"),
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
    }

    @Test
    fun `should return validation error when amount is below minimum`() {
        mockMvc
            .perform(
                get("/api/arbitrage/plans")
                    .param("amount", "0.00")
                    .param("currency", "USD"),
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
    }

    @Test
    fun `should return validation error when amount is negative`() {
        mockMvc
            .perform(
                get("/api/arbitrage/plans")
                    .param("amount", "-0.01")
                    .param("currency", "USD"),
            ).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
    }
}
