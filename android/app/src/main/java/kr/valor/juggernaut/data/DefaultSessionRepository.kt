package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kr.valor.juggernaut.common.LiftCategory
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

    override fun getAllSessions(): Flow<List<Session>> =
        sessionDataSource.getAllSessionEntities().map { sessionEntities ->
            sessionMapper.map(sessionEntities)
        }

    override fun findSessionsByUserProgression(userProgression: UserProgression): Flow<List<Session>> =
        sessionDataSource.findSessionEntitiesByUserProgression(userProgression).map { entities ->
            sessionMapper.map(entities)
        }

    override suspend fun findSessionById(sessionId: Long): Session {
        val sessionEntity = sessionDataSource.findSessionEntityById(sessionId)
        return sessionMapper.map(sessionEntity)
    }

    // considering RoomDatabase.withTransaction
    override suspend fun synchronizeSessions(userProgression: UserProgression, userTrainingMaxes: List<UserTrainingMax>) {
        sessionDataSource.findSessionEntitiesByUserProgressionOrNull(userProgression)?.let { entities ->
            if (entities.size != LiftCategory.TOTAL_LIFT_CATEGORY_COUNT) {
                entities.forEach { entity ->
                    sessionDataSource.deleteSessionEntity(entity)
                }
                initWeeklySession(userProgression, userTrainingMaxes)
            }
        } ?: initWeeklySession(userProgression, userTrainingMaxes)
    }

    override suspend fun clear() {
        sessionDataSource.clear()
    }

    // considering RoomDatabase.withTransaction
    private suspend fun initWeeklySession(userProgression: UserProgression, userTrainingMaxes: List<UserTrainingMax>) {
        userTrainingMaxes.forEach { userTrainingMax ->
            val newSessionEntity = SessionEntity(
                methodCycle = userProgression.methodCycle.value,
                phaseName = userProgression.phase.name,
                microCycleName = userProgression.microCycle.name,
                liftCategoryName = userTrainingMax.liftCategory.name,
                baseWeights = userTrainingMax.trainingMaxWeights
            )
            sessionDataSource.insertSessionEntity(newSessionEntity)
        }
    }

}