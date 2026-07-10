package com.marcioos.edgefinder.sports.domain

import java.util.UUID

data class Sport(
    val name: String,
    val id: UUID = UUID.randomUUID(),
)

data class Competition(
    val name: String,
    val sport: Sport,
    val level: CompetitionLevel,
    val format: CompetitionFormat,
    val id: UUID,
)

enum class CompetitionLevel {
    LOCAL,
    REGIONAL,
    NATIONAL,
    CONTINENTAL,
    INTERNATIONAL,
    GLOBAL,
}

enum class CompetitionFormat {
    LEAGUE,
    CUP,
    TOURNAMENT,
}
