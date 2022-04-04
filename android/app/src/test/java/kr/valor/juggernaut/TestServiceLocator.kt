package kr.valor.juggernaut

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.DefaultSessionRepository
import kr.valor.juggernaut.data.DefaultProgressionStateRepository
import kr.valor.juggernaut.data.DefaultTrainingMaxRepository
import kr.valor.juggernaut.data.session.mapper.DefaultSessionEntityMapper
import kr.valor.juggernaut.data.session.mapper.SessionMapper
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.InMemoryRoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.common.converter.KgWeightUnitTransformer
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.session.mapper.delegate.routine.BasicMethodRoutineProviderDelegate
import kr.valor.juggernaut.data.session.FakeSessionDataSource
import kr.valor.juggernaut.data.progression.FakeProgressionStateDataSource
import kr.valor.juggernaut.data.trainingmax.FakeTrainingMaxDataSource
import kr.valor.juggernaut.data.trainingmax.mapper.DefaultTrainingMaxMapper
import kr.valor.juggernaut.data.trainingmax.mapper.TrainingMaxMapper
import kr.valor.juggernaut.domain.session.repository.SessionRepository
import kr.valor.juggernaut.domain.progression.repository.ProgressionStateRepository
import kr.valor.juggernaut.domain.trainingmax.repository.TrainingMaxRepository
import kr.valor.juggernaut.domain.session.model.SessionProgression as Progression

object TestServiceLocator {

    val sessionRepository: SessionRepository by lazy {
        DefaultSessionRepository(
            provideSessionMapper(),
            FakeSessionDataSource()
        )
    }

    val userTrainingMaxRepository: TrainingMaxRepository by lazy {
        DefaultTrainingMaxRepository(
            provideUserTrainingMaxMapper(),
            FakeTrainingMaxDataSource()
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

    private fun provideUserTrainingMaxMapper(): TrainingMaxMapper {
        return DefaultTrainingMaxMapper()
    }

}