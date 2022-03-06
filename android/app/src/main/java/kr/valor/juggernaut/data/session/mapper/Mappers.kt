package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutinesProviderDelegate
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.entity.extractProgressionsInformation
import kr.valor.juggernaut.domain.session.model.*

interface ModelMapper<E, D> {
    fun map(entity: E): D
}

interface EntityModelMapper<E, D>: ModelMapper<E, D>

class DefaultEntityModelMapper(
    routinesProviderDelegate: RoutinesProviderDelegate
) : EntityModelMapper<SessionEntity, AmrapSession>, RoutinesProviderDelegate by routinesProviderDelegate {

    override fun map(entity: SessionEntity): AmrapSession {
        return with(entity) {
            val progressions = extractProgressionsInformation()
            val liftCategory = LiftCategory.valueOf(liftCategoryName)
            val routines = provideRoutines(progressions.phase, tmWeights)

            TODO()
        }
    }
}