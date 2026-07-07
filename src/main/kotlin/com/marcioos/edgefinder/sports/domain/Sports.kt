package com.marcioos.edgefinder.sports.domain

import com.marcioos.edgefinder.common.domain.DateRange
import java.time.ZonedDateTime
import java.util.*

data class Sport(
    val id: UUID,
    val name: String
)

data class League(
    val id: UUID,
    val name: String,
    val sport: Sport
)

data class Season(
    val id: UUID,
    val league: League,
    val period: DateRange,
    val competitors: Set<Competitor>
)

data class Event(
    val id: UUID,
    val season: Season,
    val home: Competitor,
    val away: Competitor,
    val dateTime: ZonedDateTime) {

    val name: String
        get() = "${home.name} vs ${away.name}"
}

data class Competitor(
    val id: UUID,
    val name: String
)
