package com.marcioos.edgefinder.sports.domain

import com.marcioos.edgefinder.common.domain.DateRange
import java.time.ZonedDateTime
import java.util.*

data class Season(
    val league: League,
    val period: DateRange,
    val competitors: Set<Competitor>,
    val id: UUID = UUID.randomUUID()
)

data class Event(
    val season: Season,
    val home: Competitor,
    val away: Competitor,
    val dateTime: ZonedDateTime,
    val id: UUID = UUID.randomUUID()
) {

    val name: String
        get() = "${home.name} vs ${away.name}"
}

data class Competitor(
    val name: String,
    val id: UUID = UUID.randomUUID()
)
