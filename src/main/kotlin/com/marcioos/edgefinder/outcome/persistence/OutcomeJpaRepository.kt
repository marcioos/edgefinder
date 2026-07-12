package com.marcioos.edgefinder.outcome.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OutcomeJpaRepository : JpaRepository<OutcomeEntity, UUID>
