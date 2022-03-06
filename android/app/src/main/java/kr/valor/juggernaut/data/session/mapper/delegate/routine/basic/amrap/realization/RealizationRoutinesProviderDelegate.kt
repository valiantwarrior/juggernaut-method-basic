package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.amrap.realization

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.amrap.AMRAPRoutineIntensityFactory
import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.amrap.AMRAPRoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.RoutineIntensity

class RealizationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutineIntensities: Map<Phase, List<RoutineIntensity>> =
        warmupRoutineIntensityMap

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        amrapRoutineIntensityMap

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object: AMRAPRoutineIntensityFactory() {
        override val warmupRoutineIntensityMap: Map<Phase, List<RoutineIntensity>> = mapOf(
            REP10   to listOf(5 to 0.5, 3 to 0.6, 1 to 0.7),
            REP8    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75),
            REP5    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8),
            REP3    to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8, 1 to 0.85)
        ).mapValues { it.value.toRoutineIntensity() }

        override val amrapRoutineIntensityMap: Map<Phase, RoutineIntensity> = mapOf(
            REP10   to (10 to 0.75),
            REP8    to (8 to 0.8),
            REP5    to (5 to 0.85),
            REP3    to (3 to 0.9)
        ).mapValues { it.value.toRoutineIntensity() }
    }
}