package com.marcioos.edgefinder.odds.persistence

import com.marcioos.edgefinder.odds.domain.Odds

interface OddsRepository {
    fun findCurrentOdds(): List<Odds>

    fun saveAll(odds: Iterable<Odds>)
}
