package com.marcioos.edgefinder.fixtures

import com.marcioos.edgefinder.odds.persistence.OddsEntity
import com.marcioos.edgefinder.odds.persistence.SportsbookEntity
import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.OutcomeSide
import com.marcioos.edgefinder.outcome.persistence.OutcomeEntity
import com.marcioos.edgefinder.sports.domain.Sport
import com.marcioos.edgefinder.sports.persistence.EventEntity
import com.marcioos.edgefinder.sports.persistence.SeasonEntity
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

object PersistenceFixtures {
    fun seasonEntity(
        id: UUID = UUID.randomUUID(),
        competition: String = "NBA",
        sport: Sport = Sport.BASKETBALL,
        start: LocalDate = LocalDate.of(2025, 10, 1),
        end: LocalDate = LocalDate.of(2026, 6, 30),
    ): SeasonEntity =
        SeasonEntity(
            id = id,
            competition = competition,
            sport = sport,
            start = start,
            end = end,
        )

    fun eventEntity(
        id: UUID = UUID.randomUUID(),
        season: SeasonEntity = seasonEntity(),
        home: String = "Lakers",
        away: String = "Celtics",
        startTime: Instant = Instant.parse("2025-12-25T20:00:00Z"),
    ): EventEntity =
        EventEntity(
            id = id,
            season = season,
            homeCompetitor = home,
            awayCompetitor = away,
            startTime = startTime,
        )

    fun outcomeEntity(
        id: UUID = UUID.randomUUID(),
        event: EventEntity = eventEntity(),
        market: MarketType = MarketType.MONEYLINE,
        side: OutcomeSide = OutcomeSide.HOME,
    ): OutcomeEntity =
        OutcomeEntity(
            id = id,
            event = event,
            market = market,
            side = side,
        )

    fun sportsbookEntity(
        id: UUID = UUID.randomUUID(),
        name: String = "DraftKings",
    ): SportsbookEntity =
        SportsbookEntity(
            id = id,
            name = name,
        )

    fun oddsEntity(
        id: UUID = UUID.randomUUID(),
        sportsbook: SportsbookEntity = sportsbookEntity(),
        outcome: OutcomeEntity = outcomeEntity(),
        decimalOdds: BigDecimal = BigDecimal("2.20"),
        updatedAt: Instant = Instant.parse("2025-12-25T18:00:00Z"),
    ): OddsEntity =
        OddsEntity(
            id = id,
            sportsbook = sportsbook,
            outcome = outcome,
            decimalOdds = decimalOdds,
            updatedAt = updatedAt,
        )
}
