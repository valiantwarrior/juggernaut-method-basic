package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.*
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.property.RoutinesPropertyMediateDelegate

class BasicIntensificationRoutinesProviderDelegate(
    routinesPropertyMediateDelegate: RoutinesPropertyMediateDelegate
): AMRAPRoutinesProviderDelegate(), RoutinesPropertyMediateDelegate by routinesPropertyMediateDelegate {

    override val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>> =
        initWarmupRoutineIntensity(warmupRoutineIntensityTable)

    override val amrapRoutineIntensity: Map<Phase, RoutineIntensity> =
        initAmrapRoutineIntensity(amrapRoutineIntensityTable)

    override val routinesPropertyMediateAction: (Double) -> Double = ::mediate


    companion object: AMRAPRoutineIntensityTableFactory() {
        override val warmupRoutineIntensityTable: PhaseWarmupRoutineIntensityTable = mapOf(
            REP10   to listOf(5 to 0.55, 5 to 0.625, 10 to 0.675, 10 to 0.675),
            REP8    to listOf(3 to 0.6, 3 to 0.675, 8 to 0.725, 8 to 0.725),
            REP5    to listOf(2 to 0.65, 2 to 0.725, 5 to 0.775, 5 to 0.775, 5 to 0.775),
            REP3    to listOf(1 to 0.7, 1 to 0.775, 3 to 0.825, 3 to 0.825, 3 to 0.825, 3 to 0.825)
        )

        override val amrapRoutineIntensityTable: PhaseAmrapRoutineIntensityTable = mapOf(
            REP10   to (10 to 0.675),
            REP8    to (8 to 0.725),
            REP5    to (5 to 0.775),
            REP3    to (3 to 0.825)
        )
    }
}