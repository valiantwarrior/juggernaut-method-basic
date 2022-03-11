package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.common.EntityMapper
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.*
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

interface SessionMapper: EntityMapper<SessionEntity, Session> {
    fun map(model: Session, params: SessionRecord?): SessionEntity
    fun map(entities: List<SessionEntity>): List<Session>
}

data class SessionRecord(
    val repetitionsRecord: Int?,
    val completeTimeMillisRecord: Long
)

class DefaultSessionEntityMapper(
    routineProviderDelegate: RoutineProviderDelegate<Progression>
) : SessionMapper, RoutineProviderDelegate<Progression> by routineProviderDelegate {

    override fun map(entity: SessionEntity): Session {
        return with(entity) {
            val category = LiftCategory.valueOf(liftCategoryName)
            val progression = Progression(
                phase = Phase.valueOf(phaseName),
                microCycle = MicroCycle.valueOf(microCycleName)
            )
            val sessionRoutine = provideSessionRoutine(progression, baseWeights, amrapRepetitions)

            Session(
                sessionId = id,
                category = category,
                methodCycle = methodCycle,
                tmWeights = baseWeights,
                progression = progression,
                routines = sessionRoutine
            )
        }
    }

    override fun map(entities: List<SessionEntity>): List<Session> =
        entities.map { map(it) }

    override fun map(model: Session, params: SessionRecord?): SessionEntity =
        with(model) {
            SessionEntity(
                id = sessionId,
                methodCycle = methodCycle,
                phaseName = progression.phase.name,
                microCycleName = progression.microCycle.name,
                liftCategoryName = category.name,
                baseWeights = tmWeights,
                amrapRepetitions = params?.repetitionsRecord,
                completeDateMillis = params?.completeTimeMillisRecord
            )
        }
}