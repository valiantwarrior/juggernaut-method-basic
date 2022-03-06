package kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.routine.basic.BasicMethodRoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.RoutineIntensity
import kr.valor.juggernaut.domain.session.model.SessionRoutine
import kr.valor.juggernaut.domain.session.model.AmrapSession.AmrapSessionRoutine as AmrapSessionRoutine

abstract class AMRAPRoutinesProviderDelegate: BasicMethodRoutinesProviderDelegate() {

    protected abstract val warmupRoutineIntensities: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensity: Map<Phase, RoutineIntensity>

    abstract override val routinesPropertyMediateAction: (Double) -> Double

    final override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        val currentPhaseWarmupRoutineIntensities = warmupRoutineIntensities[phase]!!
        val currentPhaseAmrapRoutineIntensity = amrapRoutineIntensity[phase]!!
        val transform = routinesPropertyMediateAction

        return AmrapSessionRoutine(
            warmupRoutines = currentPhaseWarmupRoutineIntensities.createRoutine(tmWeights, transform),
            amrapRoutine = currentPhaseAmrapRoutineIntensity.createRoutine(tmWeights, transform)
        )
    }
}