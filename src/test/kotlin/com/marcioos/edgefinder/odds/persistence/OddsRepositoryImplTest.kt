package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.fixtures.Fixtures
import com.marcioos.edgefinder.fixtures.PersistenceFixtures
import com.marcioos.edgefinder.outcome.persistence.OutcomeJpaRepository
import com.marcioos.edgefinder.outcome.persistence.toEntity
import com.marcioos.edgefinder.sports.persistence.toEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class OddsRepositoryImplTest {
    @Mock
    lateinit var oddsJpaRepository: OddsJpaRepository

    @Mock
    lateinit var sportsbookRepository: SportsbookJpaRepository

    @Mock
    lateinit var outcomeRepository: OutcomeJpaRepository

    @InjectMocks
    lateinit var repository: OddsRepositoryImpl

    @Test
    fun `should find current odds`() {
        val entity = PersistenceFixtures.oddsEntity()

        whenever(oddsJpaRepository.findCurrentOdds())
            .thenReturn(listOf(entity))

        val odds = repository.findCurrentOdds()

        assertThat(odds)
            .containsExactly(entity.toDomain())
    }

    @Test
    fun `should save odds`() {
        val odds = Fixtures.moneylineMarketOdds("1.95", "2.12").odds.first()

        val sportsbookEntity = odds.sportsbook.toEntity()
        val outcomeEntity =
            odds.outcome.toEntity(
                odds.outcome.event.toEntity(
                    odds.outcome.event.season
                        .toEntity(),
                ),
            )

        whenever(sportsbookRepository.getReferenceById(odds.sportsbook.id))
            .thenReturn(sportsbookEntity)

        whenever(outcomeRepository.getReferenceById(odds.outcome.id))
            .thenReturn(outcomeEntity)

        repository.saveAll(listOf(odds))

        val captor = argumentCaptor<List<OddsEntity>>()

        verify(oddsJpaRepository).saveAll(captor.capture())

        assertThat(captor.firstValue).hasSize(1)

        val saved = captor.firstValue.single()

        assertThat(saved.id)
            .isEqualTo(odds.id)

        assertThat(saved.sportsbook)
            .isSameAs(sportsbookEntity)

        assertThat(saved.outcome)
            .isSameAs(outcomeEntity)

        assertThat(saved.decimalOdds)
            .isEqualByComparingTo(odds.decimalOdds.value)

        assertThat(saved.updatedAt)
            .isEqualTo(odds.updatedAt)
    }
}
