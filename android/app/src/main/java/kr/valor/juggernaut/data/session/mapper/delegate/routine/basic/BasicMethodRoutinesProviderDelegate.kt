package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.routine.RoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.RoutineIntensity
import kr.valor.juggernaut.domain.session.model.SessionRoutine

abstract class BasicMethodRoutinesProviderDelegate: RoutinesProviderDelegate {
    protected abstract val routinesPropertyMediateAction: (Double) -> Double

    abstract override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine

    protected inline fun List<RoutineIntensity>.createRoutine(tmWeights: Double, transform: (Double) -> Double) =
        map { it.createRoutine(tmWeights, transform) }

    protected inline fun RoutineIntensity.createRoutine(tmWeights: Double, transform: (Double) -> Double) =
        Routine(
            weights = transform(tmWeights * intensityPercentage),
            reps = repetitions
        )

}