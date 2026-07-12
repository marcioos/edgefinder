package com.marcioos.edgefinder.sports.domain

import com.marcioos.edgefinder.common.domain.DateRange
import java.time.Instant
import java.util.UUID

data class Season(
    val id: UUID = UUID.randomUUID(),
    val competition: Competition,
    val period: DateRange,
)

data class Event(
    val id: UUID = UUID.randomUUID(),
    val season: Season,
    val home: Competitor,
    val away: Competitor,
    val startTime: Instant,
) {
    val name: String
        get() = "${home.name} vs ${away.name}"
}

data class Competition(
    val name: String,
    val sport: Sport,
)

enum class Sport {
    BASKETBALL,
    AMERICAN_FOOTBALL,
    FOOTBALL,
}

@JvmInline
value class Competitor(
    val name: String,
)
