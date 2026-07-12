package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.odds.domain.Odds
import org.springframework.stereotype.Repository

@Repository
class OddsRepositoryImpl(
    private val repository: OddsJpaRepository,
) : OddsRepository {
    override fun findCurrentOdds(): List<Odds> = repository.findCurrentOdds().map(OddsEntity::toDomain)

    override fun saveAll(odds: Iterable<Odds>) {
        repository.saveAll(odds.map { it.toEntity() })
    }
}
