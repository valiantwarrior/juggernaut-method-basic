package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.Phase.*
import kr.valor.juggernaut.data.session.mapper.delegate.routine.common.RoutineIntensityFactory
import kr.valor.juggernaut.domain.session.model.RoutineIntensity

abstract class AMRAPRoutineIntensityFactory: RoutineIntensityFactory() {

    protected abstract val warmupRoutineIntensityMap: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensityMap: Map<Phase, RoutineIntensity>

    final override val routineIntensityMap: Map<Phase, List<RoutineIntensity>> by lazy {
        assembleOverallRoutineIntensityTable()
    }

    private fun assembleOverallRoutineIntensityTable(): Map<Phase, List<RoutineIntensity>> = mapOf(
        REP10   to warmupRoutineIntensityMap[REP10]!! +
                amrapRoutineIntensityMap[REP10]!!,

        REP8   to warmupRoutineIntensityMap[REP8]!! +
                amrapRoutineIntensityMap[REP8]!!,

        REP5   to warmupRoutineIntensityMap[REP5]!! +
                amrapRoutineIntensityMap[REP5]!!,

        REP3   to warmupRoutineIntensityMap[REP3]!! +
                amrapRoutineIntensityMap[REP3]!!
    )
}