package kr.valor.juggernaut.data.session.mapper

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutinesProviderDelegate
import kr.valor.juggernaut.data.session.model.SessionEntity
import kr.valor.juggernaut.data.session.model.extractProgressionsInformation
import kr.valor.juggernaut.domain.session.model.*

interface ModelMapper<E, D> {
    fun map(entity: E): D
}

interface EntityModelMapper<E, D>: ModelMapper<E, D>

class DefaultEntityModelMapper(
    routinesProviderDelegate: RoutinesProviderDelegate
) : EntityModelMapper<SessionEntity, Session>, RoutinesProviderDelegate by routinesProviderDelegate {

    override fun map(entity: SessionEntity): Session {
        return with(entity) {
            val progressions = extractProgressionsInformation()
            val liftCategory = LiftCategory.valueOf(liftCategoryName)
            val routines = provideRoutines(progressions.phase, tmWeights)

            Session(
               sessionId = id,
               category = liftCategory,
               progressions = progressions,
               tmWeights = tmWeights,
               routines = routines
            )
        }
    }
}