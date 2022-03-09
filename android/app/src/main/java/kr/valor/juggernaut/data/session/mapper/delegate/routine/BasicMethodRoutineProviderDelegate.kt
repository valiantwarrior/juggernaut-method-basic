package kr.valor.juggernaut.data.session.mapper.delegate.routine

import kr.valor.juggernaut.common.MicroCycle
import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinePropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.RoutineProviderDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.intensity.RoutineIntensitySource
import kr.valor.juggernaut.domain.session.model.*
import kr.valor.juggernaut.domain.session.model.Session.Progression as Progression

class BasicMethodRoutineProviderDelegate(
    private val routineIntensitySource: RoutineIntensitySource<MicroCycle, Phase>, // In Memory source
    routinePropertyMediateDelegate: RoutinePropertyMediateDelegate // run time injection
): RoutineProviderDelegate<Progression>, RoutinePropertyMediateDelegate by routinePropertyMediateDelegate {

    override fun provideSessionRoutine(progression: Progression, tmWeights: Int, actualRepetitions: Int?): List<Routine> {
        val (phase, microCycle) = progression
        val routineIntensities = routineIntensitySource.provideRoutineIntensityMap(microCycle)[phase]!!

        return if (microCycle == MicroCycle.DELOAD) {
            routineIntensities.map { it.toRoutineModel(tmWeights = tmWeights, transform = ::mediate) }
        } else {
            val warmupRoutines = routineIntensities.dropLast(1).map {
                it.toRoutineModel(tmWeights = tmWeights, transform = ::mediate)
            }
            val amrapRoutine = routineIntensities.last().toRoutineModel(
                tmWeights = tmWeights,
                actualRepetitions = actualRepetitions,
                transform = ::mediate
            )

            warmupRoutines + amrapRoutine
        }
    }

}