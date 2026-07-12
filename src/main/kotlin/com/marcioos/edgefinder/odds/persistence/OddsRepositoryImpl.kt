package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.odds.domain.Odds
import com.marcioos.edgefinder.outcome.persistence.OutcomeJpaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class OddsRepositoryImpl(
    private val repository: OddsJpaRepository,
    private val sportsbookRepository: SportsbookJpaRepository,
    private val outcomeRepository: OutcomeJpaRepository,
) : OddsRepository {
    override fun findCurrentOdds(): List<Odds> = repository.findCurrentOdds().map(OddsEntity::toDomain)

    @Transactional
    override fun saveAll(odds: Iterable<Odds>) {
        repository.saveAll(
            odds.map {
                it.toEntity(
                    sportsbookRepository.getReferenceById(it.sportsbook.id),
                    outcomeRepository.getReferenceById(it.outcome.id),
                )
            },
        )
    }
}
