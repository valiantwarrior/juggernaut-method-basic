package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

interface SessionMapper: EntityMapper<SessionEntity, Session> {

    fun mapModel(model: Session, params: SessionRecord?): SessionEntity

}

class DefaultSessionEntityMapper @Inject constructor(
    routineProviderDelegate: RoutineProviderDelegate<Progression>
) : SessionMapper, RoutineProviderDelegate<Progression> by routineProviderDelegate {

    override fun mapEntity(entity: SessionEntity): Session {
        return with(entity) {
            val category = LiftCategory.valueOf(liftCategoryName)
            val progression = Progression(
                phase = Phase.valueOf(phaseName),
                microCycle = MicroCycle.valueOf(microCycleName),
                methodCycle = MethodCycle(methodCycleValue)
            )
            val sessionRoutine = provideSessionRoutine(progression, baseWeights, amrapRepetitions)

            Session(
                sessionId = id,
                category = category,
                tmWeights = baseWeights,
                progression = progression,
                isCompleted = entity.completeDateMillis != null,
                completedLocalDateTime = completeDateMillis?.toLocalDateTime(),
                sessionOrdinal = sessionOrdinal,
                routines = sessionRoutine
            )
        }
    }

    override fun mapModel(model: Session, params: SessionRecord?): SessionEntity =
        with(model) {
            SessionEntity(
                id = sessionId,
                methodCycleValue = progression.methodCycle.value,
                phaseName = progression.phase.name,
                microCycleName = progression.microCycle.name,
                liftCategoryName = category.name,
                baseWeights = tmWeights,
                amrapRepetitions = params?.repetitionsRecord,
                completeDateMillis = params?.completeTimeMillisRecord,
                sessionOrdinal = params?.sessionOrdinal
            )
        }

    private fun Long.toLocalDateTime() =
        Instant.ofEpochMilli(this).let { instant ->
            LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        }

}