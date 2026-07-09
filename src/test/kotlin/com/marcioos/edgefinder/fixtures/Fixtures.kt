package com.marcioos.edgefinder.fixtures

import com.marcioos.edgefinder.common.domain.DateRange
import com.marcioos.edgefinder.common.domain.Money
import com.marcioos.edgefinder.common.domain.Ratio
import com.marcioos.edgefinder.odds.domain.DecimalOdds
import com.marcioos.edgefinder.odds.domain.MarketOdds
import com.marcioos.edgefinder.odds.domain.Odds
import com.marcioos.edgefinder.odds.domain.Sportsbook
import com.marcioos.edgefinder.outcome.domain.MoneylineMarket
import com.marcioos.edgefinder.outcome.domain.MoneylineOutcome
import com.marcioos.edgefinder.sports.domain.*
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

object Fixtures {

    val basketball = Sport(
        "Basketball",
        UUID.fromString("00000000-0000-0000-0000-000000000001")
    )

    val nba = League(
        "NBA",
        basketball,
        UUID.fromString("00000000-0000-0000-0000-000000000002")
    )

    val season2026 = Season(
        nba,
        DateRange(LocalDate.of(2025, 10, 1), LocalDate.of(2026, 6, 30)),
        emptySet(),
        UUID.fromString("00000000-0000-0000-0000-000000000003")
    )

    val lakers = Competitor(
        "Los Angeles Lakers",
        UUID.fromString("00000000-0000-0000-0000-000000000010")
    )

    val celtics = Competitor(
        "Boston Celtics",
        UUID.fromString("00000000-0000-0000-0000-000000000011")
    )

    val knicks = Competitor(
        "New York Knicks",
        UUID.fromString("00000000-0000-0000-0000-000000000012")
    )

    val grizzlies = Competitor(
        "Memphis Grizzlies",
        UUID.fromString("00000000-0000-0000-0000-000000000013")
    )

    val lakersVsCeltics = Event(
        season2026,
        lakers,
        celtics,
        ZonedDateTime.parse("2026-01-10T20:00:00Z"),
        UUID.fromString("00000000-0000-0000-0000-000000000020")
    )

    val knicksVsGrizzlies = Event(
        season2026,
        knicks,
        grizzlies,
        ZonedDateTime.parse("2026-01-13T20:00:00Z"),
        UUID.fromString("00000000-0000-0000-0000-000000000021")
    )

    val fistMarket = MoneylineMarket(
        lakersVsCeltics,
        UUID.fromString("00000000-0000-0000-0000-000000000030")
    )

    val secondMarket = MoneylineMarket(
        knicksVsGrizzlies,
        UUID.fromString("00000000-0000-0000-0000-000000000031")
    )

    val draftKings = Sportsbook(
        "DraftKings",
        UUID.fromString("00000000-0000-0000-0000-000000000040")
    )

    val fanDuel = Sportsbook(
        "FanDuel",
        UUID.fromString("00000000-0000-0000-0000-000000000041")
    )

    fun moneylineMarketOdds(
        decimalOdds1: String,
        decimalOdds2: String,
        market: MoneylineMarket = fistMarket
    ): MarketOdds {

        val lakersOutcome = MoneylineOutcome(
            market,
            lakers,
            UUID.randomUUID()
        )

        val celticsOutcome = MoneylineOutcome(
            market,
            celtics,
            UUID.randomUUID()
        )

        return MarketOdds(
            market,
            listOf(
                Odds(
                    id = UUID.randomUUID(),
                    sportsbook = draftKings,
                    outcome = lakersOutcome,
                    decimalOdds = DecimalOdds(BigDecimal(decimalOdds1)),
                    updatedAt = Instant.now()
                ),
                Odds(
                    id = UUID.randomUUID(),
                    sportsbook = fanDuel,
                    outcome = celticsOutcome,
                    decimalOdds = DecimalOdds(BigDecimal(decimalOdds2)),
                    updatedAt = Instant.now()
                )
            )
        )
    }

    fun money(value: String) =
        Money(BigDecimal(value))

    fun ratio(value: String) =
        Ratio(BigDecimal(value))
}
val MarketOdds.homeOdds
    get() = this.odds.first()

val MarketOdds.awayOdds
    get() = this.odds[1]