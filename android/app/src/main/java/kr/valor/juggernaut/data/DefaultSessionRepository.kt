package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.SessionRecord
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.data.user.source.UserProgressionDataSource
import kr.valor.juggernaut.data.user.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.model.UserProgression

class DefaultSessionRepository(
    private val sessionMapper: SessionMapper<SessionEntity, Session>,
    private val sessionDataSource: SessionDataSource,
    private val userProgressionDataSource: UserProgressionDataSource,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource
): SessionRepository {

    private val userProgressionFlow: Flow<UserProgression> = userProgressionDataSource
        .getUserProgressionData()

    override suspend fun insertSessionEntity(sessionEntity: SessionEntity) {
        sessionDataSource.insertSessionEntity(sessionEntity)
    }

    override suspend fun createSessionEntity(): SessionEntity {
        val (currentMethodCycle, currentPhase, currentMicroCycle, currentLiftCategory) =
            userProgressionFlow.first()
        val tmWeights = userTrainingMaxDataSource.getUserTrainingMaxEntityByLiftCategory(currentLiftCategory)
            .trainingMaxWeights

        return SessionEntity(
            methodCycle = currentMethodCycle,
            phaseName = currentPhase.name,
            microCycleName = currentMicroCycle.name,
            liftCategoryName = currentLiftCategory.name,
            baseWeights = tmWeights
        )
    }
}