package com.marcioos.edgefinder.outcome.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAll
import com.marcioos.edgefinder.common.persistence.persistAndReload
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class OutcomeEntityIT : JpaIntegrationTest() {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should persist and load outcome`() {
        val season = PersistenceFixtures.seasonEntity()
        val event = PersistenceFixtures.eventEntity(season = season)
        entityManager.persistAll(season, event)
        val outcome = PersistenceFixtures.outcomeEntity(event = event)

        val loaded =
            entityManager.persistAndReload(
                outcome,
                OutcomeEntity::class.java,
                outcome.id,
            )

        assertThat(loaded.event.id).isEqualTo(outcome.event.id)
        assertThat(loaded.event.season.id).isEqualTo(outcome.event.season.id)
        assertThat(loaded.side).isEqualTo(outcome.side)
        assertThat(loaded.market).isEqualTo(outcome.market)
    }
}
