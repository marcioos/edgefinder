package com.marcioos.edgefinder.sports.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAll
import com.marcioos.edgefinder.common.persistence.persistAndReload
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class EventEntityIT : JpaIntegrationTest() {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should persist and load event`() {
        val season = PersistenceFixtures.seasonEntity()
        entityManager.persistAll(season)

        val event = PersistenceFixtures.eventEntity(season = season)
        val loaded =
            entityManager.persistAndReload(
                event,
                EventEntity::class.java,
                event.id,
            )

        assertThat(loaded.season.id).isEqualTo(event.season.id)
        assertThat(loaded.startTime).isEqualTo(event.startTime)
        assertThat(loaded.homeCompetitor).isEqualTo(event.homeCompetitor)
        assertThat(loaded.awayCompetitor).isEqualTo(event.awayCompetitor)
    }
}
