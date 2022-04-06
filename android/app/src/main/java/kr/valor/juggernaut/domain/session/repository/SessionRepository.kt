package kr.valor.juggernaut.domain.session.repository

import kotlinx.coroutines.flow.Flow
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax

interface SessionRepository {

    fun getAllSessionSummaries(): Flow<List<SessionSummary>>

    fun findSessionIdsByUserProgression(userProgression: UserProgression): Flow<List<Long>>

    suspend fun findSessionSummaryByIdOneShot(sessionId: Long): SessionSummary

    suspend fun findSessionByIdOneShot(sessionId: Long): Session

    suspend fun countCompletedSessionEntitiesBasedOnUserProgression(userProgression: UserProgression): Int

    suspend fun updateSession(session: Session, sessionRecord: SessionRecord)

    suspend fun synchronizeSessions(userProgression: UserProgression, trainingMaxes: List<TrainingMax>)

    suspend fun deleteSessionsByMethodCycle(methodCycle: MethodCycle)

    suspend fun clear()

}