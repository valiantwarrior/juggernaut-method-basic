package kr.valor.juggernaut.data.session.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity

interface SessionDataSource {

    suspend fun insertSessionEntity(entity: SessionEntity): Long

    suspend fun updateSessionEntity(entity: SessionEntity)

    suspend fun deleteSessionEntity(entity: SessionEntity)

    suspend fun deleteSessionEntitiesByMethodCycle(methodCycle: Int)

    fun findSessionEntityById(id: Long): Flow<SessionEntity>

    suspend fun findSessionEntityByIdOneShot(id: Long): SessionEntity

    suspend fun getCompletedSessionEntitiesCount(methodCycleValue: Int, phaseName: String, microCycleName: String): Int

    fun findSessionEntitiesByUserProgression(methodCycleValue: Int, phaseName: String, microCycleName: String): Flow<List<SessionEntity>>

    fun getAllSessionEntities(): Flow<List<SessionEntity>>

    suspend fun clear()

}