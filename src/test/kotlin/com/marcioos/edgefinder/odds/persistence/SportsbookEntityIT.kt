package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.common.persistence.JpaIntegrationTest
import com.marcioos.edgefinder.common.persistence.persistAndReload
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

class SportsbookEntityIT : JpaIntegrationTest() {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `should persist and load sportsbook`() {
        val sportsbook = PersistenceFixtures.sportsbookEntity()

        val loaded =
            entityManager.persistAndReload(
                sportsbook,
                SportsbookEntity::class.java,
                sportsbook.id,
            )

        assertThat(loaded.name).isEqualTo(sportsbook.name)
    }
}
