package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.odds.domain.DecimalOdds
import com.marcioos.edgefinder.odds.domain.Odds
import com.marcioos.edgefinder.outcome.persistence.OutcomeEntity
import com.marcioos.edgefinder.outcome.persistence.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "odds",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_odds_sportsbook_outcome",
            columnNames = [
                "sportsbook_id",
                "outcome_id",
            ],
        ),
    ],
)
class OddsEntity(
    @Id
    val id: UUID,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sportsbook_id")
    val sportsbook: SportsbookEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outcome_id")
    val outcome: OutcomeEntity,
    @Column(name = "decimal_odds", nullable = false, precision = 10, scale = 4)
    val decimalOdds: BigDecimal,
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant,
)

fun OddsEntity.toDomain(): Odds =
    Odds(
        id = id,
        sportsbook = sportsbook.toDomain(),
        outcome = outcome.toDomain(),
        decimalOdds = DecimalOdds(decimalOdds),
        updatedAt = updatedAt,
    )

fun Odds.toEntity(
    sportsbook: SportsbookEntity,
    outcome: OutcomeEntity,
): OddsEntity =
    OddsEntity(
        id = id,
        sportsbook = sportsbook,
        outcome = outcome,
        decimalOdds = decimalOdds.value,
        updatedAt = updatedAt,
    )
