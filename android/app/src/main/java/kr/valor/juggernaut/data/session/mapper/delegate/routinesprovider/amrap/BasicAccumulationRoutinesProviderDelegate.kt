package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity

class BasicAccumulationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutineIntensity(ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE)

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity(ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE)

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object {

        private val ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE: PhaseWarmupRoutineIntensityTable = mapOf(
            REP10   to listOf(10 to 0.6, 10 to 0.6, 10 to 0.6, 10 to 0.6),
            REP8    to listOf(8 to 0.65, 8 to 0.65, 8 to 0.65, 8 to 0.65),
            REP5    to listOf(5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7, 5 to 0.7),
            REP3    to listOf(3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75, 3 to 0.75)
        )

        private val ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE: PhaseAmrapRoutineIntensityTable = mapOf(
            REP10   to (10 to 0.6),
            REP8    to (8 to 0.65),
            REP5    to (5 to 0.7),
            REP3    to (3 to 0.75)
        )

        val ACCUMULATION_CYCLE_OVERALL_ROUTINE_INTENSITY_TABLE: PhaseOverallRoutineIntensityTable = mapOf(
            REP10   to ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP10]!! +
                    ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP10]!!,

            REP8    to ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP8]!! +
                    ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP8]!!,

            REP5    to ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP5]!! +
                    ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP5]!!,

            REP3    to ACCUMULATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP3]!! +
                    ACCUMULATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP3]!!
        )
    }
}