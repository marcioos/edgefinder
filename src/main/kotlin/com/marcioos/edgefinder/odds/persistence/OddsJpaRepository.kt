package com.marcioos.edgefinder.odds.persistence

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface OddsJpaRepository : JpaRepository<OddsEntity, UUID> {
    @EntityGraph(
        attributePaths = [
            "sportsbook",
            "outcome",
            "outcome.event",
            "outcome.event.season",
        ],
    )
    @Query("SELECT o FROM OddsEntity o")
    fun findCurrentOdds(): List<OddsEntity>
}
