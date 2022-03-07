package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.domain.session.model.*
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

interface ModelMapper<E, D> {
    fun map(entity: E): D
}

interface EntityModelMapper<E, D>: ModelMapper<E, D>

class DefaultEntityModelMapper(
    routineProviderDelegate: RoutineProviderDelegate<Progression>
) : EntityModelMapper<SessionEntity, Session>, RoutineProviderDelegate<Progression> by routineProviderDelegate {

    override fun map(entity: SessionEntity): Session {
        return with(entity) {
            val category = LiftCategory.valueOf(liftCategoryName)
            val progression = Progression(
                phase = Phase.valueOf(phaseName),
                microCycle = MicroCycle.valueOf(microCycleName)
            )
            val sessionRoutine = provideSessionRoutine(progression, tmWeights)

            Session(
                sessionId = id,
                category = category,
                tmWeights = tmWeights,
                progression = progression,
                routines = sessionRoutine
            )
        }
    }
}