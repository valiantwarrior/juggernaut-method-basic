package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine as SessionRoutine
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine.Routine as Routine

typealias PhaseWarmupRoutineIntensityMap = Map<Phase, List<Pair<Int, Double>>>
typealias PhaseAmrapRoutineIntensityMap = Map<Phase, Pair<Int, Double>>

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

    protected fun initWarmupRoutineIntensity(intensities: PhaseWarmupRoutineIntensityMap): Map<Phase, List<RoutineIntensity>> =
        mutableMapOf<Phase, List<RoutineIntensity>>().apply {
            Phase.values().forEach { phase ->
                val warmupRoutineIntensities = mutableListOf<RoutineIntensity>()
                    .apply {
                        val routineIntensities = intensities[phase]!!

                        routineIntensities.forEach { (repetitions, intensity) ->
                            val routineIntensity = RoutineIntensity(
                                intensityPercentages = intensity,
                                repetitions = repetitions
                            )
                            add(routineIntensity)
                        }
                    }
                put(phase, warmupRoutineIntensities)
            }
        }.toMap()

    protected fun initAmrapRoutineIntensity(intensities: PhaseAmrapRoutineIntensityMap): Map<Phase, RoutineIntensity> =
        mutableMapOf<Phase, RoutineIntensity>().apply {
            Phase.values().forEach { phase ->
                val (amrapRoutineBaseRepetitions, amrapRoutineIntensityPercentages) =
                    intensities[phase]!!
                val amrapRoutineIntensity = RoutineIntensity(
                    intensityPercentages = amrapRoutineIntensityPercentages,
                    repetitions = amrapRoutineBaseRepetitions
                )
                put(phase, amrapRoutineIntensity)
            }
        }.toMap()
}

