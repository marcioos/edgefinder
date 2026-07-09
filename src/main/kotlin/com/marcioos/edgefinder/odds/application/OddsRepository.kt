package com.marcioos.edgefinder.odds.application

import com.marcioos.edgefinder.odds.domain.Odds

interface OddsRepository {
    fun findCurrentOdds(): List<Odds>
}
