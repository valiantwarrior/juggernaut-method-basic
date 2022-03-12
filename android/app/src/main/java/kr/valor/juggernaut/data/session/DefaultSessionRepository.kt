package kr.valor.juggernaut.data.session

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

class DefaultSessionRepository(
    private val sessionMapper: SessionMapper,
    private val sessionDataSource: SessionDataSource
): SessionRepository {

    override fun getLatestSession(): Flow<Session> =
        sessionDataSource.getLatestSessionEntity().map { sessionEntity ->
            sessionMapper.map(sessionEntity)
        }

    override fun getAllSessions(): Flow<List<Session>> =
        sessionDataSource.getAllSessionEntities().map { sessionEntities ->
            sessionMapper.map(sessionEntities)
        }

    override suspend fun findSessionById(sessionId: Long): Session {
        val sessionEntity = sessionDataSource.findSessionEntityById(sessionId)
        return sessionMapper.map(sessionEntity)
    }

    override suspend fun synchronizeSession(userProgression: UserProgression, userTrainingMax: UserTrainingMax) {
        sessionDataSource.getLatestSessionEntityOrNull()?.let { entity ->
            val userProgressionFromEntity = entity.getUserProgression()
            if (userProgressionFromEntity != userProgression) {
                initSession(userProgression, userTrainingMax)
            }
        } ?: initSession(userProgression, userTrainingMax)
    }

    override suspend fun clear() {
        sessionDataSource.clear()
    }

    private suspend fun initSession(userProgression: UserProgression, userTrainingMax: UserTrainingMax) {
        val newSessionEntity = SessionEntity(
            methodCycle = userProgression.methodCycle.value,
            phaseName = userProgression.phase.name,
            microCycleName = userProgression.microCycle.name,
            liftCategoryName = userProgression.liftCategory.name,
            baseWeights = userTrainingMax.trainingMaxWeights
        )
        sessionDataSource.insertSessionEntity(newSessionEntity)
    }

    private fun SessionEntity.getUserProgression(): UserProgression =
        UserProgression(
            methodCycle = MethodCycle(methodCycle),
            phase = Phase.valueOf(phaseName),
            microCycle = MicroCycle.valueOf(microCycleName),
            liftCategory = LiftCategory.valueOf(liftCategoryName)
        )
}