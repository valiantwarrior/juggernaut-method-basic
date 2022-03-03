package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*

import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensityChartProvider

class BasicAccumulationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutineIntensity(ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE)

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity(ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE)

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object {
        private val ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE = mapOf(
            REP10 to listOf(10 to 0.6, 10 to 0.6, 10 to 0.6, 10 to 0.6),
            REP8 to listOf(8 to 0.65, 8 to 0.65, 8 to 0.65, 8 to 0.65),
            REP5 to listOf(5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7),
            REP3 to listOf(3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75)
        )

        private val ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE = mapOf(
            REP10 to (10 to 0.6),
            REP8 to (8 to 0.65),
            REP5 to (5 to 0.7),
            REP3 to (3 to 0.75)
        )
    }
}