package kr.valor.juggernaut

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.DefaultSessionRepository
import kr.valor.juggernaut.data.DefaultUserRepository
import kr.valor.juggernaut.data.session.entity.SessionEntity
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.property.DefaultPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinePropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.source.FakeSessionDataSource
import kr.valor.juggernaut.data.user.mapper.DefaultUserTrainingMaxMapper
import kr.valor.juggernaut.data.user.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.source.FakeUserProgressionDataSource
import kr.valor.juggernaut.data.user.source.FakeUserTrainingMaxDataSource
import kr.valor.juggernaut.domain.session.model.Session
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.repository.UserRepository
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

    fun provideSessionMapper(): SessionMapper {
        return DefaultSessionEntityMapper(provideRoutineProviderDelegate())
    }

    fun provideSessionRepository(): SessionRepository {
        return DefaultSessionRepository(
            provideSessionMapper(),
            FakeSessionDataSource()
        )
    }

    fun provideUserTrainingMaxMapper(): UserTrainingMaxMapper {
        return DefaultUserTrainingMaxMapper()
    }

    fun provideUserRepository(): UserRepository {
        return DefaultUserRepository(
            provideUserTrainingMaxMapper(),
            FakeUserTrainingMaxDataSource(),
            FakeUserProgressionDataSource()
        )
    }

}