package kr.valor.juggernaut.data.session.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.progression.model.UserProgression

interface SessionDataSource {

    suspend fun insertSessionEntity(entity: SessionEntity): Long

    suspend fun updateSessionEntity(entity: SessionEntity)

    suspend fun deleteSessionEntity(entity: SessionEntity)

    suspend fun deleteSessionEntitiesByMethodCycle(methodCycle: Int)

    suspend fun findSessionEntityById(id: Long): SessionEntity

    fun findSessionEntitiesByUserProgression(methodCycleValue: Int, phaseName: String, microCycleName: String): Flow<List<SessionEntity>>

    fun getAllSessionEntities(): Flow<List<SessionEntity>>

    suspend fun clear()

}