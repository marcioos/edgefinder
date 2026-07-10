package com.marcioos.edgefinder.sports.domain

import com.marcioos.edgefinder.fixtures.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class EventTest {
    @Test
    fun `should generate event name from competitors`() {
        val event =
            Event(
                season = Fixtures.season2026,
                home = Fixtures.lakers,
                away = Fixtures.celtics,
                startTime = ZonedDateTime.parse("2026-01-10T20:00:00Z"),
            )

        assertThat(event.name).isEqualTo("Los Angeles Lakers vs Boston Celtics")
    }
}
