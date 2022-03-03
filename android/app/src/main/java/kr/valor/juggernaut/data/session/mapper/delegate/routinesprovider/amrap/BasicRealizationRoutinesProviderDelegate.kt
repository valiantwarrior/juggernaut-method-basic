package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity

class BasicRealizationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutineIntensity(REALIZATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE)

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity(REALIZATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE)

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object {
        val REALIZATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE = mapOf(
            REP10 to listOf(5 to 0.5, 3 to 0.6, 1 to 0.7),
            REP8 to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75),
            REP5 to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8),
            REP3 to listOf(5 to 0.5, 3 to 0.6, 2 to 0.7, 1 to 0.75, 1 to 0.8, 1 to 0.85),
        )

        val REALIZATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE = mapOf(
            REP10 to (10 to 0.75),
            REP8 to (8 to 0.8),
            REP5 to (5 to 0.85),
            REP3 to (3 to 0.9),
        )
    }
}