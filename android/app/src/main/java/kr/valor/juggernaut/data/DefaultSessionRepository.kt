package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.SessionSummaryMapper
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.domain.progression.model.UserProgression
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.trainingmax.model.TrainingMax
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSessionRepository @Inject constructor(
    private val sessionMapper: SessionMapper,
    private val sessionSummaryMapper: SessionSummaryMapper,
    private val sessionDataSource: SessionDataSource
): SessionRepository {

    override fun getAllSessionSummaries(): Flow<List<SessionSummary>> =
        sessionDataSource.getAllSessionEntities().map { entities ->
            entities
                .filter { it.completeDateMillis != null }
                .getOrEmptyList { it.toSummarizedDomainModel() }
        }

    override fun findSessionIdsByUserProgression(userProgression: UserProgression): Flow<List<Long>> {
        val (methodCycleValue, phaseName, microCycleName) =
            userProgression.serializedValue

        return sessionDataSource.findSessionEntitiesByUserProgression(
            methodCycleValue, phaseName, microCycleName
        ).map { entities ->
            entities.getOrEmptyList { it.id }
        }
    }

    override suspend fun findSessionSummaryByIdOneShot(sessionId: Long): SessionSummary =
        sessionDataSource.findSessionEntityByIdOneShot(sessionId).toSummarizedDomainModel()

    override suspend fun findSessionByIdOneShot(sessionId: Long): Session =
        sessionDataSource.findSessionEntityByIdOneShot(sessionId).toDomainModel()

    override suspend fun countCompletedSessionEntitiesBasedOnUserProgression(userProgression: UserProgression): Int {
        val (methodCycleValue, phaseName, microCycleName) =
            userProgression.serializedValue

        return sessionDataSource.getCompletedSessionEntitiesCount(methodCycleValue, phaseName, microCycleName)
    }

    override suspend fun updateSession(session: Session, sessionRecord: SessionRecord) {
        val sessionEntity = session.toDatabaseModel(sessionRecord)
        sessionDataSource.updateSessionEntity(sessionEntity)
    }

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

    private fun SessionEntity.toSummarizedDomainModel(): SessionSummary =
        sessionSummaryMapper.mapEntity(this)

    private inline fun <T, R> List<T>.getOrEmptyList(transform: (T) -> R): List<R> =
        if (isEmpty()) emptyList() else map { transform(it) }

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