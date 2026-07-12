package com.marcioos.edgefinder.outcome.persistence

import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.MoneylineOutcome
import com.marcioos.edgefinder.outcome.domain.Outcome
import com.marcioos.edgefinder.outcome.domain.OutcomeSide
import com.marcioos.edgefinder.sports.persistence.EventEntity
import com.marcioos.edgefinder.sports.persistence.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "outcome")
class OutcomeEntity(
    @Id
    val id: UUID,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    val event: EventEntity,
    @Enumerated(EnumType.STRING)
    @Column(name = "market_type", nullable = false)
    val marketType: MarketType,
    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false)
    val side: OutcomeSide,
)

fun OutcomeEntity.toDomain(): Outcome =
    when (marketType) {
        MarketType.MONEYLINE -> {
            MoneylineOutcome(
                id = id,
                event = event.toDomain(),
                side = side,
            )
        }
    }

fun Outcome.toEntity(event: EventEntity): OutcomeEntity =
    when (this) {
        is MoneylineOutcome -> {
            OutcomeEntity(
                id = id,
                event = event,
                marketType = market,
                side = side,
            )
        }
    }
