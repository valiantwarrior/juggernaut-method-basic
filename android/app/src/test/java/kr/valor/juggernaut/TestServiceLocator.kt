package kr.valor.juggernaut

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.DefaultSessionRepository
import kr.valor.juggernaut.data.DefaultProgressionStateRepository
import kr.valor.juggernaut.data.DefaultUserTrainingMaxRepository
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.common.converter.KgWeightUnitTransformer
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.FakeSessionDataSource
import kr.valor.juggernaut.data.user.progression.FakeProgressionStateDataSource
import kr.valor.juggernaut.data.user.trainingmax.FakeUserTrainingMaxDataSource
import kr.valor.juggernaut.data.user.trainingmax.mapper.DefaultUserTrainingMaxMapper
import kr.valor.juggernaut.data.user.trainingmax.mapper.UserTrainingMaxMapper
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.user.repository.ProgressionStateRepository
import kr.valor.juggernaut.domain.user.repository.UserTrainingMaxRepository
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

object TestServiceLocator {

    val sessionRepository: SessionRepository by lazy {
        DefaultSessionRepository(
            provideSessionMapper(),
            FakeSessionDataSource()
        )
    }

    val userTrainingMaxRepository: UserTrainingMaxRepository by lazy {
        DefaultUserTrainingMaxRepository(
            provideUserTrainingMaxMapper(),
            FakeUserTrainingMaxDataSource(),
            provideWeightUnitTransformer()
        )
    }

    val progressionStateRepository: ProgressionStateRepository by lazy {
        DefaultProgressionStateRepository(
            FakeProgressionStateDataSource()
        )
    }

    fun provideWeightUnitTransformer(): WeightUnitTransformer =
        KgWeightUnitTransformer()

    fun provideRoutineIntensitySource(): RoutineIntensitySource<MicroCycle, Phase> =
        InMemoryRoutineIntensitySource()

    fun provideRoutineProviderDelegate(): RoutineProviderDelegate<Progression> =
        BasicMethodRoutineProviderDelegate(
            provideRoutineIntensitySource(), provideWeightUnitTransformer()
        )

    fun provideSessionMapper(): SessionMapper {
        return DefaultSessionEntityMapper(provideRoutineProviderDelegate())
    }

    private fun provideUserTrainingMaxMapper(): UserTrainingMaxMapper {
        return DefaultUserTrainingMaxMapper()
    }

}