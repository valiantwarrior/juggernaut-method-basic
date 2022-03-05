package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.createPhaseBasedKeyMapAndReturn
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.*
import kr.valor.juggernaut.domain.session.model.AmrapSession.AmrapSessionRoutine as AmrapSessionRoutine

abstract class AMRAPRoutinesProviderDelegate: BasicMethodRoutinesProviderDelegate() {

    protected abstract val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensity: Map<Phase, RoutineIntensity>

    abstract override val routinesPropertyMediateAction: (Double) -> Double

    final override fun provideRoutines(phase: Phase, tmWeights: Double): AmrapSessionRoutine {
        val warmupRoutineIntensitiesOfCurrentPhase = warmupRoutinesIntensities[phase]!!
        val amrapRoutineIntensityOfCurrentPhase = amrapRoutineIntensity[phase]!!
        val transform = routinesPropertyMediateAction

        return AmrapSessionRoutine(
            warmupRoutines = warmupRoutineIntensitiesOfCurrentPhase.createRoutine(tmWeights, transform),
            amrapRoutine = amrapRoutineIntensityOfCurrentPhase.createRoutine(tmWeights, transform)
        )
    }

    protected fun initWarmupRoutineIntensity(intensities: PhaseWarmupRoutineIntensityTable): Map<Phase, List<RoutineIntensity>> =
        createPhaseBasedKeyMapAndReturn { phase ->
            intensities[phase]!!.toRoutineIntensityModel()
        }

    protected fun initAmrapRoutineIntensity(intensities: PhaseAmrapRoutineIntensityTable): Map<Phase, RoutineIntensity> =
        createPhaseBasedKeyMapAndReturn { phase ->
            intensities[phase]!!.toRoutineIntensityModel()
        }
}