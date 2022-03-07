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

    override fun provideSessionRoutine(progression: Progression, tmWeights: Double): List<Routine> =
        with(progression) {
            routineIntensitySource
                .provideRoutineIntensityMap(microCycle)[phase]!!
                .toRoutineModels(tmWeights, ::mediate)
        }
}