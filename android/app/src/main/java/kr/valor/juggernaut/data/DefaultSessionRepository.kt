package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.*
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSessionRepository @Inject constructor(
    private val sessionMapper: SessionMapper,
    private val sessionDataSource: SessionDataSource
): SessionRepository {

    override fun getAllSessions(): Flow<List<Session>> =
        sessionDataSource.getAllSessionEntities().map { entities ->
            entities.map { it.toDomainModel() }
        }

    override fun findSessionsByUserProgression(userProgression: UserProgression): Flow<List<Session>> {
        val (methodCycleValue, phaseName, microCycleName) =
            userProgression.serializedValue

        return sessionDataSource.findSessionEntitiesByUserProgression(
            methodCycleValue, phaseName, microCycleName
        ).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun findSessionById(sessionId: Long): Session =
        sessionDataSource.findSessionEntityById(sessionId).toDomainModel()

    // considering RoomDatabase.withTransaction
    override suspend fun synchronizeSessions(userProgression: UserProgression, trainingMaxes: List<TrainingMax>) {
        val (methodCycleValue, phaseName, microCycleName) =
            userProgression.serializedValue

        val sessionEntities = sessionDataSource.findSessionEntitiesByUserProgression(
            methodCycleValue, phaseName, microCycleName
        ).first()

        when(sessionEntities.isEmpty()) {
            true -> initWeeklySession(userProgression, trainingMaxes)
            false -> {
                if (sessionEntities.size != LiftCategory.TOTAL_LIFT_CATEGORY_COUNT) {
                    sessionEntities.forEach { entity ->
                        sessionDataSource.deleteSessionEntity(entity)
                    }
                    initWeeklySession(userProgression, trainingMaxes)
                }
            }
        }
    }

    override suspend fun deleteSessionsByMethodCycle(methodCycle: MethodCycle) {
        sessionDataSource.deleteSessionEntitiesByMethodCycle(methodCycle.value)
    }

    override suspend fun clear() {
        sessionDataSource.clear()
    }

    private fun Session.toDatabaseModel(sessionRecord: SessionRecord?): SessionEntity =
        sessionMapper.mapModel(this, sessionRecord)

    private fun SessionEntity.toDomainModel(): Session =
        sessionMapper.mapEntity(this)

    // considering RoomDatabase.withTransaction
    private suspend fun initWeeklySession(userProgression: UserProgression, trainingMaxes: List<TrainingMax>) {
        trainingMaxes.forEach { userTrainingMax ->
            val newSessionEntity = SessionEntity(
                methodCycleValue = userProgression.methodCycle.value,
                phaseName = userProgression.phase.name,
                microCycleName = userProgression.microCycle.name,
                liftCategoryName = userTrainingMax.liftCategory.name,
                baseWeights = userTrainingMax.trainingMaxWeights
            )
            sessionDataSource.insertSessionEntity(newSessionEntity)
        }
    }

}