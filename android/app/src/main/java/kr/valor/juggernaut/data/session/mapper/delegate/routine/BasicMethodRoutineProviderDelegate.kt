package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.converter.WeightUnitTransformer
import kr.valor.juggernaut.data.common.di.KgWeightUnit
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.data.session.mapper.di.InMemorySource
import kr.valor.juggernaut.domain.session.model.*
import javax.inject.Inject

class BasicMethodRoutineProviderDelegate @Inject constructor(
    @InMemorySource private val routineIntensitySource: RoutineIntensitySource<MicroCycle, Phase>,
    @KgWeightUnit private val transformer: WeightUnitTransformer // run time injection (maybe)
): RoutineProviderDelegate<SessionProgression> {

    override fun provideSessionRoutine(progression: SessionProgression, tmWeights: Int, actualRepetitions: Int?): List<Routine> {
        val (_, phase, microCycle) = progression
        val routineIntensities = routineIntensitySource.provideRoutineIntensityMap(microCycle)[phase]!!

        return if (microCycle == MicroCycle.DELOAD) {
            routineIntensities.map { it.toRoutineModel(tmWeights = tmWeights, transform = transformer::transform) }
        } else {
            val warmupRoutines = routineIntensities.dropLast(1).map {
                it.toRoutineModel(tmWeights = tmWeights, transform = transformer::transform)
            }
            val amrapRoutine = routineIntensities.last().toRoutineModel(
                tmWeights = tmWeights,
                actualRepetitions = actualRepetitions,
                transform = transformer::transform
            )

            warmupRoutines + amrapRoutine
        }
    }

}