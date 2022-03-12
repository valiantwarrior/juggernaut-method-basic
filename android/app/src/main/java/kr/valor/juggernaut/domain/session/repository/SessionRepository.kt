package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface SessionRepository {
    fun getSession(): Flow<Session>
    fun getAllSessions(): Flow<List<Session>>
    suspend fun getSessionById(sessionId: Long): Session
    suspend fun synchronizeSession(userProgression: UserProgression, userTrainingMax: UserTrainingMax)
    suspend fun clear()
}