package kr.valor.juggernaut

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.SessionRecord
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinePropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

object TestServiceLocator {

    fun provideRoutinePropertyMediateDelegate(): RoutinePropertyMediateDelegate =
        DefaultPropertyMediateDelegate

    fun provideRoutineIntensitySource(): RoutineIntensitySource<MicroCycle, Phase> =
        InMemoryRoutineIntensitySource()

    fun provideRoutineProviderDelegate(): RoutineProviderDelegate<Progression> =
        BasicMethodRoutineProviderDelegate(
            provideRoutineIntensitySource(), provideRoutinePropertyMediateDelegate()
        )

    fun provideEntityModelMapper(): SessionMapper<SessionEntity, Session> {
        return DefaultSessionEntityMapper(provideRoutineProviderDelegate())
    }
}