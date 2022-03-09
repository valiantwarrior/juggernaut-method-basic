package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity

interface SessionRepository {
    suspend fun createSessionEntity(): SessionEntity
    suspend fun insertSessionEntity(sessionEntity: SessionEntity)
//    fun getLatestSessionEntity(): Flow<SessionEntity>
}