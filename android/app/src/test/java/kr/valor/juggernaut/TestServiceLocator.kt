package kr.valor.juggernaut

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.DefaultSessionRepository
import kr.valor.juggernaut.data.user.DefaultUserRepository
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.common.converter.KgWeightUnitTransformer
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.source.FakeSessionDataSource
import kr.valor.juggernaut.data.user.mapper.DefaultUserTrainingMaxMapper
import kr.valor.juggernaut.data.user.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.data.user.source.FakeUserProgressionDataSource
import kr.valor.juggernaut.data.user.source.FakeUserTrainingMaxDataSource
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.session.usecases.SynchronizeSessionUseCase
import kr.valor.juggernaut.domain.session.usecases.SynchronizeSessionUseCaseImpl
import kr.valor.juggernaut.domain.user.repository.UserRepository
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

object TestServiceLocator {

    val sessionRepository: SessionRepository by lazy {
        DefaultSessionRepository(
            provideSessionMapper(),
            FakeSessionDataSource()
        )
    }

    val userRepository: UserRepository by lazy {
        DefaultUserRepository(
            provideUserTrainingMaxMapper(),
            FakeUserTrainingMaxDataSource(),
            FakeUserProgressionDataSource()
        )
    }



    fun provideRoutinePropertyMediateDelegate(): WeightUnitTransformer =
        KgWeightUnitTransformer()

    fun provideRoutineIntensitySource(): RoutineIntensitySource<MicroCycle, Phase> =
        InMemoryRoutineIntensitySource()

    fun provideRoutineProviderDelegate(): RoutineProviderDelegate<Progression> =
        BasicMethodRoutineProviderDelegate(
            provideRoutineIntensitySource(), provideRoutinePropertyMediateDelegate()
        )

    fun provideSessionMapper(): SessionMapper {
        return DefaultSessionEntityMapper(provideRoutineProviderDelegate())
    }

    private fun provideUserTrainingMaxMapper(): UserTrainingMaxMapper {
        return DefaultUserTrainingMaxMapper()
    }


    fun provideSynchronizeSessionUseCase(): SynchronizeSessionUseCase {
        val getUserProgressionUseCase = userRepository::getUserProgression
        val getUserTrainingMaxUseCase = userRepository::getUserTrainingMax

        return SynchronizeSessionUseCaseImpl(sessionRepository, getUserProgressionUseCase, getUserTrainingMaxUseCase)
    }

}