package com.marcioos.edgefinder.odds.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SportsbookJpaRepository : JpaRepository<SportsbookEntity, UUID>
