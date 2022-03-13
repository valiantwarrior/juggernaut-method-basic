package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

interface SessionRepository {

    fun getAllSessions(): Flow<List<Session>>

    fun findSessionsByUserProgression(userProgression: UserProgression): Flow<List<Session>>

    suspend fun findSessionById(sessionId: Long): Session

    suspend fun synchronizeSessions(userProgression: UserProgression, userTrainingMaxes: List<UserTrainingMax>)

    suspend fun clear()

}