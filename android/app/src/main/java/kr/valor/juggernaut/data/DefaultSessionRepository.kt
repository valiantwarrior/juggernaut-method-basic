package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.model.UserProgression
import kr.valor.juggernaut.domain.user.model.UserTrainingMax

class DefaultSessionRepository(
    private val sessionMapper: SessionMapper,
    private val sessionDataSource: SessionDataSource
): SessionRepository {

    override fun getSession(): Flow<Session> =
        sessionDataSource.getSessionEntity().map { sessionEntity ->
            sessionMapper.map(sessionEntity)
        }

    override fun getAllSessions(): Flow<List<Session>> =
        sessionDataSource.getAllSessionEntities().map { sessionEntities ->
            sessionMapper.map(sessionEntities)
        }

    override suspend fun getSessionById(sessionId: Long): Session {
        val sessionEntity = sessionDataSource.getSessionEntityById(sessionId)
        return sessionMapper.map(sessionEntity)
    }

    override suspend fun synchronizeSession(userProgression: UserProgression, userTrainingMax: UserTrainingMax) {
        sessionDataSource.getLatestSessionEntity()?.let { entity ->
            val userProgressionFromEntity = entity.getUserProgression()
            if (userProgressionFromEntity != userProgression) {
                initSession(userProgression, userTrainingMax)
            }
        } ?: initSession(userProgression, userTrainingMax)
    }

    private suspend fun initSession(userProgression: UserProgression, userTrainingMax: UserTrainingMax) {
        val newSessionEntity = SessionEntity(
            methodCycle = userProgression.methodCycle,
            phaseName = userProgression.phase.name,
            microCycleName = userProgression.microCycle.name,
            liftCategoryName = userProgression.liftCategory.name,
            baseWeights = userTrainingMax.trainingMaxWeights
        )
        sessionDataSource.insertSessionEntity(newSessionEntity)
    }

    private fun SessionEntity.getUserProgression(): UserProgression =
        UserProgression(
            methodCycle = methodCycle,
            phase = Phase.valueOf(phaseName),
            microCycle = MicroCycle.valueOf(microCycleName),
            liftCategory = LiftCategory.valueOf(liftCategoryName)
        )
}