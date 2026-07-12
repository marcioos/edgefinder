package com.marcioos.edgefinder.outcome.persistence

import com.marcioos.edgefinder.outcome.domain.MarketType
import com.marcioos.edgefinder.outcome.domain.MoneylineOutcome
import com.marcioos.edgefinder.outcome.domain.Outcome
import com.marcioos.edgefinder.sports.domain.Competitor
import com.marcioos.edgefinder.sports.persistence.EventEntity
import com.marcioos.edgefinder.sports.persistence.toDomain
import com.marcioos.edgefinder.sports.persistence.toEntity
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
    @Column(name = "competitor", nullable = true)
    val competitor: String?,
)

fun OutcomeEntity.toDomain(): Outcome =
    when (marketType) {
        MarketType.MONEYLINE -> {
            MoneylineOutcome(
                id = id,
                event = event.toDomain(),
                competitor = Competitor(competitor!!),
            )
        }
    }

fun Outcome.toEntity(): OutcomeEntity =
    OutcomeEntity(
        id = id,
        event = event.toEntity(),
        marketType = market,
        competitor = (this as? MoneylineOutcome)?.competitor?.name,
    )
