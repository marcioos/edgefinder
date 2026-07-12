package com.marcioos.edgefinder.sports.persistence

import com.marcioos.edgefinder.common.domain.DateRange
import com.marcioos.edgefinder.sports.domain.Competition
import com.marcioos.edgefinder.sports.domain.Season
import com.marcioos.edgefinder.sports.domain.Sport
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(
    name = "season",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_season_competition",
            columnNames = [
                "competition",
                "start",
                "end",
            ],
        ),
    ],
)
class SeasonEntity(
    @Id
    val id: UUID,
    @Column(name = "start", nullable = false)
    val start: LocalDate,
    @Column(name = "end", nullable = false)
    val end: LocalDate,
    @Enumerated(EnumType.STRING)
    @Column(name = "sport", nullable = false)
    val sport: Sport,
    @Column(name = "competition", nullable = false)
    val competition: String,
)

fun SeasonEntity.toDomain(): Season =
    Season(
        id = id,
        competition = Competition(competition, sport),
        period = DateRange(start, end),
    )

fun Season.toEntity(): SeasonEntity =
    SeasonEntity(
        id = id,
        start = period.start,
        end = period.end,
        sport = competition.sport,
        competition = competition.name,
    )
