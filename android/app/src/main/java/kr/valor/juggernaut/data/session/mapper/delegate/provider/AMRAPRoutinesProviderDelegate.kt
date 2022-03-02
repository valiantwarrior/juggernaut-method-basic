package kr.valor.juggernaut.data.session.mapper.delegate.provider

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine as SessionRoutine
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine.Routine as Routine

abstract class AMRAPRoutinesProviderDelegate: RoutinesProviderDelegate {

    protected abstract val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensity: Map<Phase, RoutineIntensity>

    protected abstract val routinesPropertyMediateAction: (Double) -> Double

    override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
        val warmupRoutineIntensitiesOfCurrentPhase = warmupRoutinesIntensities[phase]!!
        val prRoutineIntensityOfCurrentPhase = amrapRoutineIntensity[phase]!!

        val warmupRoutines = mutableListOf<Routine>()
            .apply {
                val totalWarmupRoutinesNumber = warmupRoutineIntensitiesOfCurrentPhase.size

                repeat(totalWarmupRoutinesNumber) { index ->
                    val (intensityPercentages, repetitions) = warmupRoutineIntensitiesOfCurrentPhase[index]
                    val routineWeightsByIntensity = routinesPropertyMediateAction(tmWeights * intensityPercentages)
                    val warmupRoutine = Routine(
                        weights = routineWeightsByIntensity,
                        reps = repetitions
                    )
                    add(warmupRoutine)
                }
            }
            .toList()

        val amrapRoutine = with(prRoutineIntensityOfCurrentPhase) {
            val (intensityPercentages, repetitions) = this

            Routine(
                weights = routinesPropertyMediateAction(tmWeights * intensityPercentages),
                reps = repetitions
            )
        }

        return SessionRoutine(
            warmupRoutines = warmupRoutines,
            amrapRoutine = amrapRoutine
        )
    }

    protected data class RoutineIntensity(
        val intensityPercentages: Double,
        val repetitions: Int
    )
}