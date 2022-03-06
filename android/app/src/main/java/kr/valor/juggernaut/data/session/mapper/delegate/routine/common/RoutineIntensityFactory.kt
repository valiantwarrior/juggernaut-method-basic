package kr.valor.juggernaut.data.session.mapper.delegate.routine.common

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.model.RoutineIntensity

abstract class RoutineIntensityFactory {
    abstract val routineIntensityMap: Map<Phase, List<RoutineIntensity>>

    protected fun Pair<Int, Double>.toRoutineIntensity(): RoutineIntensity =
        RoutineIntensity(first, second)

    protected fun List<Pair<Int, Double>>.toRoutineIntensity(): List<RoutineIntensity> =
        map { it.toRoutineIntensity() }
}