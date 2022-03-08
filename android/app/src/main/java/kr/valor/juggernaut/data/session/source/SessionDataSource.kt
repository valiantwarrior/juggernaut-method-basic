package kr.valor.juggernaut.data.session.source

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity

interface SessionDataSource {
    suspend fun insertSessionEntity(entity: SessionEntity)
    suspend fun updateSessionEntity(entity: SessionEntity)
    fun getSessionEntityById(id: Long): Flow<SessionEntity>
}