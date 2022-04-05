package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.SessionProgression
import kr.valor.juggernaut.domain.session.model.SessionRecord
import kr.valor.juggernaut.ui.common.toLocalDateTime
import javax.inject.Inject

class DefaultSessionEntityMapper @Inject constructor(
    routineProviderDelegate: @JvmSuppressWildcards RoutineProviderDelegate<SessionProgression, List<Routine>>
) : SessionMapper, RoutineProviderDelegate<SessionProgression, List<Routine>> by routineProviderDelegate {

    override fun mapEntity(entity: SessionEntity): Session {
        return with(entity) {
            val category = LiftCategory.valueOf(liftCategoryName)
            val progression = SessionProgression(
                phase = Phase.valueOf(phaseName),
                microCycle = MicroCycle.valueOf(microCycleName),
                methodCycle = MethodCycle(methodCycleValue)
            )
            val sessionRoutine = provideSessionRoutine(progression, baseWeights, amrapRepetitions)

            Session(
                sessionId = id,
                category = category,
                tmWeights = baseWeights,
                sessionProgression = progression,
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
                methodCycleValue = sessionProgression.methodCycle.value,
                phaseName = sessionProgression.phase.name,
                microCycleName = sessionProgression.microCycle.name,
                liftCategoryName = category.name,
                baseWeights = tmWeights,
                amrapRepetitions = params?.repetitionsRecord,
                completeDateMillis = params?.completeTimeMillisRecord,
                sessionOrdinal = params?.sessionOrdinal
            )
        }

}