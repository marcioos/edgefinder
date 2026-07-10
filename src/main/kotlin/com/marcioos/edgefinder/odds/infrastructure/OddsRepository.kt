package com.marcioos.edgefinder.odds.infrastructure

import com.marcioos.edgefinder.odds.domain.Odds

interface OddsRepository {
    fun findCurrentOdds(): List<Odds>
}
