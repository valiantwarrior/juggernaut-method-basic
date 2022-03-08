package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session

interface SessionRepository {
    suspend fun updateSession(session: Session)
    suspend fun insertSession(session: Session)
    suspend fun getSessionById(sessionId: Long): Flow<Session>
}