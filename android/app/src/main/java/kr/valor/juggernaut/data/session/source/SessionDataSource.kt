package kr.valor.juggernaut.data.session.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.user.model.UserProgression

interface SessionDataSource {

    suspend fun insertSessionEntity(entity: SessionEntity): Long

    suspend fun updateSessionEntity(entity: SessionEntity)

    suspend fun deleteSessionEntity(entity: SessionEntity)

    suspend fun findSessionEntityById(id: Long): SessionEntity

    suspend fun getLatestSessionEntityOrNull(): SessionEntity?

    suspend fun findSessionEntitiesByUserProgressionOrNull(userProgression: UserProgression): List<SessionEntity>?

    fun findSessionEntitiesByUserProgression(userProgression: UserProgression): Flow<List<SessionEntity>>

    fun getLatestSessionEntity(): Flow<SessionEntity>

    fun getAllSessionEntities(): Flow<List<SessionEntity>>

    suspend fun clear()
}