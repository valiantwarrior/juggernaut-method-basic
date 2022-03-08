package kr.valor.juggernaut.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.EntityModelMapper
import kr.valor.juggernaut.data.session.source.SessionDataSource
import kr.valor.juggernaut.data.user.source.UserProgressionDataSource
import kr.valor.juggernaut.data.user.source.UserTrainingMaxDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.model.UserProgression

class DefaultSessionRepository(
    private val entityModelMapper: EntityModelMapper<SessionEntity, Session>,
    private val sessionDataSource: SessionDataSource,
    private val userProgressionDataSource: UserProgressionDataSource,
    private val userTrainingMaxDataSource: UserTrainingMaxDataSource
): SessionRepository {

    val userProgressionFlow: Flow<UserProgression> = userProgressionDataSource
        .getUserProgressionData()

    override suspend fun updateSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun insertSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionById(sessionId: Long): Flow<Session> {
        TODO("Not yet implemented")
    }

    suspend fun createSessionEntity(): SessionEntity {
        val (currentPhase, currentMicroCycle, currentLiftCategory) =
            userProgressionFlow.first()
        val tmWeights = userTrainingMaxDataSource.getUserTrainingMaxEntityByLiftCategory(currentLiftCategory)
            .trainingMaxWeights

        return SessionEntity(
            liftCategoryName = currentLiftCategory.name,
            phaseName = currentPhase.name,
            microCycleName = currentMicroCycle.name,
            tmWeights = tmWeights
        )
    }
}