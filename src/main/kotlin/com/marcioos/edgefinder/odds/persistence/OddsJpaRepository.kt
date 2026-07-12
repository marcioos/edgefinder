package com.marcioos.edgefinder.odds.persistence

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OddsJpaRepository : JpaRepository<OddsEntity, UUID> {
    @EntityGraph(
        attributePaths = [
            "sportsbook",
            "outcome",
            "outcome.event",
            "outcome.event.home",
            "outcome.event.away",
        ],
    )
    fun findCurrentOdds(): List<OddsEntity>
}
