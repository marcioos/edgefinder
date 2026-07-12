package com.marcioos.edgefinder.sports.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAndReload
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class SeasonEntityIT : JpaIntegrationTest() {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should persist and load season`() {
        val season = PersistenceFixtures.seasonEntity()

        val loaded =
            entityManager.persistAndReload(
                season,
                SeasonEntity::class.java,
                season.id,
            )

        assertThat(loaded.competition).isEqualTo(season.competition)
        assertThat(loaded.sport).isEqualTo(season.sport)
        assertThat(loaded.start).isEqualTo(season.start)
        assertThat(loaded.end).isEqualTo(season.end)
    }
}
