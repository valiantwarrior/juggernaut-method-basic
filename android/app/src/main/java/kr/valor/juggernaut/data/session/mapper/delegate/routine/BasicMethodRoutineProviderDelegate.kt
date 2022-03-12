package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.common.converter.WeightUnitConversionDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.domain.session.model.*
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

class BasicMethodRoutineProviderDelegate(
    private val routineIntensitySource: RoutineIntensitySource<MicroCycle, Phase>, // In Memory source
    weightUnitConversionDelegate: WeightUnitConversionDelegate // run time injection
): RoutineProviderDelegate<Progression>, WeightUnitConversionDelegate by weightUnitConversionDelegate {

    override fun provideSessionRoutine(progression: Progression, tmWeights: Int, actualRepetitions: Int?): List<Routine> {
        val (phase, microCycle) = progression
        val routineIntensities = routineIntensitySource.provideRoutineIntensityMap(microCycle)[phase]!!

        return if (microCycle == MicroCycle.DELOAD) {
            routineIntensities.map { it.toRoutineModel(tmWeights = tmWeights, transform = ::convert) }
        } else {
            val warmupRoutines = routineIntensities.dropLast(1).map {
                it.toRoutineModel(tmWeights = tmWeights, transform = ::convert)
            }
            val amrapRoutine = routineIntensities.last().toRoutineModel(
                tmWeights = tmWeights,
                actualRepetitions = actualRepetitions,
                transform = ::convert
            )

            warmupRoutines + amrapRoutine
        }
    }

}