package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.LiftCategory
import kr.valor.juggernaut.common.MethodCycle
import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.mapper.EntityMapper
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.SessionProgression
import kr.valor.juggernaut.domain.session.model.SessionSummary
import kr.valor.juggernaut.ui.common.toLocalDateTime
import javax.inject.Inject

class SessionSummaryMapper @Inject constructor(
    routineProviderDelegate: RoutineProviderDelegate<SessionProgression, Routine>
): EntityMapper<SessionEntity, SessionSummary>, RoutineProviderDelegate<SessionProgression, Routine> by routineProviderDelegate {
    override fun mapEntity(entity: SessionEntity): SessionSummary {
        return with(entity) {
            val sessionProgression = SessionProgression(
                MethodCycle(methodCycleValue), Phase.valueOf(phaseName), MicroCycle.valueOf(microCycleName)
            )
            val liftCategory = LiftCategory.valueOf(liftCategoryName)
            val amrapRoutine = provideSessionRoutine(sessionProgression, baseWeights, amrapRepetitions)

            SessionSummary(
                sessionId = id,
                category = liftCategory,
                sessionProgression = sessionProgression,
                completedLocalDateTime = completeDateMillis?.toLocalDateTime(),
                amrapRoutine = amrapRoutine
            )
        }
    }
}