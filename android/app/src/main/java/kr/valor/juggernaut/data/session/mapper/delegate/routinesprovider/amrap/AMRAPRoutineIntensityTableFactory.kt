package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.PhaseEntireRoutineIntensityTable
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.PhaseRoutineIntensityItem
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensityTableFactory

typealias PhaseWarmupRoutineIntensityTable = Map<Phase, List<PhaseRoutineIntensityItem>>
typealias PhaseAmrapRoutineIntensityTable = Map<Phase, PhaseRoutineIntensityItem>

abstract class AMRAPRoutineIntensityTableFactory: RoutineIntensityTableFactory {

    protected abstract val warmupRoutineIntensityTable: PhaseWarmupRoutineIntensityTable

    protected abstract val amrapRoutineIntensityTable: PhaseAmrapRoutineIntensityTable

    final override val entireRoutineIntensityTable: PhaseEntireRoutineIntensityTable by lazy {
        assembleOverallRoutineIntensityTable()
    }

    private fun assembleOverallRoutineIntensityTable(): PhaseEntireRoutineIntensityTable = mapOf(
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