package kr.valor.juggernaut.data.session.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.user.model.UserProgression

interface SessionDataSource {
    suspend fun insertSessionEntity(entity: SessionEntity): Long
    suspend fun updateSessionEntity(entity: SessionEntity)
    suspend fun getSessionEntityById(id: Long): SessionEntity
    suspend fun getLatestSessionEntity(): SessionEntity?
    fun getSessionEntity(): Flow<SessionEntity>
    fun getAllSessionEntities(): Flow<List<SessionEntity>>
    suspend fun clear()
}