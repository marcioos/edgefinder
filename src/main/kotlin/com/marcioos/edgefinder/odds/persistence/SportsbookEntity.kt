package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.odds.domain.Sportsbook
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "sportsbook")
class SportsbookEntity(
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
)

fun SportsbookEntity.toDomain(): Sportsbook =
    Sportsbook(
        id = id,
        name = name,
    )

fun Sportsbook.toEntity(): SportsbookEntity =
    SportsbookEntity(
        id = id,
        name = name,
    )
