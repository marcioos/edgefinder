package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAll
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Hibernate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class OddsJpaRepositoryIT : JpaIntegrationTest() {
    @Autowired
    lateinit var repository: OddsJpaRepository

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should find current odds`() {
        val season = PersistenceFixtures.seasonEntity()
        val event = PersistenceFixtures.eventEntity(season = season)
        val outcome = PersistenceFixtures.outcomeEntity(event = event)
        val sportsbook = PersistenceFixtures.sportsbookEntity()
        val odds =
            PersistenceFixtures.oddsEntity(
                sportsbook = sportsbook,
                outcome = outcome,
            )

        entityManager.persist(season)
        entityManager.persist(event)
        entityManager.persist(outcome)
        entityManager.persist(sportsbook)
        entityManager.persist(odds)

        entityManager.flush()
        entityManager.clear()

        val loaded = repository.findCurrentOdds()

        assertThat(loaded)
            .hasSize(1)

        val entity = loaded.single()

        assertThat(entity.id)
            .isEqualTo(odds.id)

        assertThat(entity.decimalOdds)
            .isEqualByComparingTo(odds.decimalOdds)

        assertThat(entity.updatedAt)
            .isEqualTo(odds.updatedAt)
    }

    @Test
    fun `should eagerly fetch entity graph`() {
        val season = PersistenceFixtures.seasonEntity()
        val event = PersistenceFixtures.eventEntity(season = season)
        val outcome = PersistenceFixtures.outcomeEntity(event = event)
        val sportsbook = PersistenceFixtures.sportsbookEntity()
        val odds =
            PersistenceFixtures.oddsEntity(
                sportsbook = sportsbook,
                outcome = outcome,
            )

        entityManager.persistAll(season, event, outcome, sportsbook, odds)

        val entity = repository.findCurrentOdds().single()

        assertThat(Hibernate.isInitialized(entity.sportsbook))
            .isTrue()

        assertThat(Hibernate.isInitialized(entity.outcome))
            .isTrue()

        assertThat(Hibernate.isInitialized(entity.outcome.event))
            .isTrue()

        assertThat(Hibernate.isInitialized(entity.outcome.event.season))
            .isTrue()

        assertThat(entity.sportsbook.name)
            .isEqualTo(sportsbook.name)

        assertThat(entity.outcome.market)
            .isEqualTo(outcome.market)

        assertThat(entity.outcome.event.homeCompetitor)
            .isEqualTo(event.homeCompetitor)

        assertThat(entity.outcome.event.season.competition)
            .isEqualTo(season.competition)
    }
}
