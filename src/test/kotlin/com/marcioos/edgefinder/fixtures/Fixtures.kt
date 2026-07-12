package com.marcioos.edgefinder.fixtures

import com.marcioos.edgefinder.arbitrage.domain.ArbitrageOpportunity
import com.marcioos.edgefinder.arbitrage.domain.ArbitragePlan
import com.marcioos.edgefinder.common.domain.DateRange
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.odds.domain.DecimalOdds
import com.marcioos.edgefinder.odds.domain.MarketOdds
import com.marcioos.edgefinder.odds.domain.Odds
import com.marcioos.edgefinder.odds.domain.Sportsbook
import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.MoneylineOutcome
import com.marcioos.edgefinder.outcome.domain.OutcomeSide
import com.marcioos.edgefinder.sports.domain.Competition
import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import com.marcioos.edgefinder.sports.domain.Season
import com.marcioos.edgefinder.sports.domain.Sport
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

object Fixtures {
    val nba =
        Competition(
            name = "NBA",
            sport = Sport.BASKETBALL,
        )

    val season2026 =
        Season(
            id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
            competition = nba,
            period = DateRange(LocalDate.of(2025, 10, 1), LocalDate.of(2026, 6, 30)),
        )

    val lakers =
        Competitor(
            name = "Los Angeles Lakers",
        )

    val celtics =
        Competitor(
            "Boston Celtics",
        )

    val knicks =
        Competitor(
            "New York Knicks",
        )

    val grizzlies =
        Competitor(
            "Memphis Grizzlies",
        )

    val lakersVsCeltics =
        Event(
            id = UUID.fromString("00000000-0000-0000-0000-000000000010"),
            season = season2026,
            home = lakers,
            away = celtics,
            startTime = Instant.parse("2026-01-10T20:00:00Z"),
        )

    val knicksVsGrizzlies =
        Event(
            id = UUID.fromString("00000000-0000-0000-0000-000000000011"),
            season = season2026,
            home = knicks,
            away = grizzlies,
            startTime = Instant.parse("2026-01-13T20:00:00Z"),
        )

    val draftKings =
        Sportsbook(
            id = UUID.fromString("00000000-0000-0000-0000-000000000020"),
            name = "DraftKings",
        )

    val fanDuel =
        Sportsbook(
            id = UUID.fromString("00000000-0000-0000-0000-000000000021"),
            name = "FanDuel",
        )

    fun moneylineMarketOdds(
        decimalOdds1: String,
        decimalOdds2: String,
        market: MarketType = MarketType.MONEYLINE,
        event: Event = lakersVsCeltics,
    ): MarketOdds {
        val homeOutcome =
            MoneylineOutcome(
                id = UUID.randomUUID(),
                market = market,
                event = event,
                side = OutcomeSide.HOME,
            )

        val awayOutcome =
            MoneylineOutcome(
                id = UUID.randomUUID(),
                market = market,
                event = event,
                side = OutcomeSide.AWAY,
            )

        return MarketOdds(
            event,
            market,
            listOf(
                Odds(
                    id = UUID.randomUUID(),
                    sportsbook = draftKings,
                    outcome = homeOutcome,
                    decimalOdds = DecimalOdds(BigDecimal(decimalOdds1)),
                    updatedAt = Instant.now(),
                ),
                Odds(
                    id = UUID.randomUUID(),
                    sportsbook = fanDuel,
                    outcome = awayOutcome,
                    decimalOdds = DecimalOdds(BigDecimal(decimalOdds2)),
                    updatedAt = Instant.now(),
                ),
            ),
        )
    }

    fun money(value: String) = Money(BigDecimal(value))

    fun ratio(value: String) = Ratio(BigDecimal(value))

    fun arbitrageOpportunity(
        marketOdds: MarketOdds =
            moneylineMarketOdds(
                decimalOdds1 = "2.20",
                decimalOdds2 = "2.15",
            ),
    ): ArbitrageOpportunity = ArbitrageOpportunity.create(marketOdds)

    fun arbitragePlan(
        opportunity: ArbitrageOpportunity = arbitrageOpportunity(),
        bankroll: Money = Money("1000"),
    ): ArbitragePlan =
        ArbitragePlan.forBankroll(
            bankroll,
            opportunity,
        )
}

val MarketOdds.homeOdds
    get() = this.odds.first()

val MarketOdds.awayOdds
    get() = this.odds[1]
