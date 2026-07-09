package com.marcioos.edgefinder.sports.domain

import java.util.*

data class Sport(
    val name: String,
    val id: UUID = UUID.randomUUID()
)

data class League(
    val name: String,
    val sport: Sport,
    val id: UUID = UUID.randomUUID()
)