package com.marcioos.edgefinder.common.persistence

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
abstract class JpaIntegrationTest {
    companion object {
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:17")).withReuse(true)
    }
}

internal fun TestEntityManager.persistAll(vararg entities: Any) {
    entities.forEach(::persist)
    flush()
    clear()
}

internal fun <T : Any> TestEntityManager.persistAndReload(
    entity: T,
    entityClass: Class<T>,
    id: Any,
): T {
    persistAndFlush(entity)
    clear()
    return find(entityClass, id)!!
}
