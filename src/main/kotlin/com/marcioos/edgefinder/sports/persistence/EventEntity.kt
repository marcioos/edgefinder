package com.marcioos.edgefinder.sports.persistence

import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.domain.Event
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "event")
class EventEntity(
    @Id
    val id: UUID,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "season_id")
    val season: SeasonEntity,
    @Column(name = "home_competitor", nullable = false)
    val homeCompetitor: String,
    @Column(name = "away_competitor", nullable = false)
    val awayCompetitor: String,
    @Column(nullable = false)
    val startTime: Instant,
)

fun EventEntity.toDomain() =
    Event(
        id = id,
        season = season.toDomain(),
        home = Competitor(homeCompetitor),
        away = Competitor(awayCompetitor),
        startTime = startTime,
    )

fun Event.toEntity() =
    EventEntity(
        id = id,
        season = season.toEntity(),
        homeCompetitor = home.name,
        awayCompetitor = away.name,
        startTime = startTime,
    )
