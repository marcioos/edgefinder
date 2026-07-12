package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAll
import com.marcioos.edgefinder.common.persistence.persistAndReload
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class OddsEntityIT : JpaIntegrationTest() {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should persist and load odds`() {
        val season = PersistenceFixtures.seasonEntity()
        val event = PersistenceFixtures.eventEntity(season = season)
        val outcome = PersistenceFixtures.outcomeEntity(event = event)
        val sportsbook = PersistenceFixtures.sportsbookEntity()
        val odds =
            PersistenceFixtures.oddsEntity(
                sportsbook = sportsbook,
                outcome = outcome,
            )

        entityManager.persistAll(season, event, outcome, sportsbook)

        val loaded =
            entityManager.persistAndReload(
                odds,
                OddsEntity::class.java,
                odds.id,
            )

        assertThat(loaded.outcome.id).isEqualTo(odds.outcome.id)
        assertThat(loaded.outcome.event.id).isEqualTo(odds.outcome.event.id)
        assertThat(loaded.outcome.event.season.id).isEqualTo(odds.outcome.event.season.id)
        assertThat(loaded.sportsbook.id).isEqualTo(odds.sportsbook.id)
        assertThat(loaded.decimalOdds).isEqualByComparingTo(odds.decimalOdds)
        assertThat(loaded.updatedAt).isEqualTo(odds.updatedAt)
    }
}
