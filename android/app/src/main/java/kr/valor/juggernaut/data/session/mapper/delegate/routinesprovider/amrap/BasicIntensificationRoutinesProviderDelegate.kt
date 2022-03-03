package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity

class BasicIntensificationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutineIntensity(INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE)

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity(INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE)

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object {

        private val INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE: PhaseWarmupRoutineIntensityTable = mapOf(
            REP10   to listOf(5 to 0.55, 5 to 0.625, 10 to 0.675, 10 to 0.675),
            REP8    to listOf(3 to 0.6, 3 to 0.675, 8 to 0.725, 8 to 0.725),
            REP5    to listOf(2 to 0.65, 2 to 0.725, 5 to 0.775, 5 to 0.775, 5 to 0.775),
            REP3    to listOf(1 to 0.7, 1 to 0.775, 3 to 0.825, 3 to 0.825, 3 to 0.825, 3 to 0.825)
        )

        private val INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE: PhaseAmrapRoutineIntensityTable = mapOf(
            REP10   to (10 to 0.675),
            REP8    to (8 to 0.725),
            REP5    to (5 to 0.775),
            REP3    to (3 to 0.825)
        )

        val INTENSIFICATION_CYCLE_OVERALL_ROUTINE_INTENSITY_TABLE: PhaseOverallRoutineIntensityTable = mapOf(
            REP10   to INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP10]!! +
                    INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP10]!!,

            REP8    to INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP8]!! +
                    INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP8]!!,

            REP5    to INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP5]!! +
                    INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP5]!!,

            REP3    to INTENSIFICATION_CYCLE_WARMUP_ROUTINE_INTENSITY_TABLE[REP3]!! +
                    INTENSIFICATION_CYCLE_AMRAP_ROUTINE_INTENSITY_TABLE[REP3]!!
        )
    }
}