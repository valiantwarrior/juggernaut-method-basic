package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*

typealias PhaseWarmupRoutineIntensityTable = Map<Phase, List<Pair<Int, Double>>>
typealias PhaseAmrapRoutineIntensityTable = Map<Phase, Pair<Int, Double>>
typealias PhaseOverallRoutineIntensityTable = Map<Phase, List<Pair<Int, Double>>>

abstract class AMRAPRoutineIntensityTableFactory {

    protected abstract val warmupRoutineIntensityTable: PhaseWarmupRoutineIntensityTable

    protected abstract val amrapRoutineIntensityTable: PhaseAmrapRoutineIntensityTable

    val overallRoutineIntensityTable: PhaseOverallRoutineIntensityTable by lazy {
        assembleOverallRoutineIntensityTable()
    }

    private fun assembleOverallRoutineIntensityTable(): PhaseOverallRoutineIntensityTable = mapOf(
        REP10   to warmupRoutineIntensityTable[REP10]!! +
                amrapRoutineIntensityTable[REP10]!!,

        REP8   to warmupRoutineIntensityTable[REP8]!! +
                amrapRoutineIntensityTable[REP8]!!,

        REP5   to warmupRoutineIntensityTable[REP5]!! +
                amrapRoutineIntensityTable[REP5]!!,

        REP3   to warmupRoutineIntensityTable[REP3]!! +
                amrapRoutineIntensityTable[REP3]!!
    )
}