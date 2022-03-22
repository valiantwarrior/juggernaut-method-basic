package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

interface SessionRepository {

    fun getAllSessions(): Flow<List<Session>>

    fun findSessionsByUserProgression(userProgression: UserProgression): Flow<List<Session>>

    suspend fun findSessionByIdOneShot(sessionId: Long): Session

    fun findSessionById(sessionId: Long): Flow<Session>

    suspend fun synchronizeSessions(userProgression: UserProgression, trainingMaxes: List<TrainingMax>)

    suspend fun deleteSessionsByMethodCycle(methodCycle: MethodCycle)

    suspend fun clear()

}