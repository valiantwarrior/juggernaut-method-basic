package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine as SessionRoutine
import kr.valor.juggernaut.domain.session.model.Session.SessionRoutine.Routine as Routine

typealias PhaseWarmupRoutineIntensityTable = Map<Phase, List<Pair<Int, Double>>>
typealias PhaseAmrapRoutineIntensityTable = Map<Phase, Pair<Int, Double>>
typealias PhaseOverallRoutineIntensityTable = Map<Phase, List<Pair<Int, Double>>>

abstract class AMRAPRoutinesProviderDelegate: RoutinesProviderDelegate {

    protected abstract val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensity: Map<Phase, RoutineIntensity>

    protected abstract val routinesPropertyMediateAction: (Double) -> Double

    final override fun provideRoutines(phase: Phase, tmWeights: Double): SessionRoutine {
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

    protected fun initWarmupRoutineIntensity(intensities: PhaseWarmupRoutineIntensityTable): Map<Phase, List<RoutineIntensity>> =
        routineIntensityMapFactory { phase ->
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
                }.toList()

            warmupRoutineIntensities
        }

    protected fun initAmrapRoutineIntensity(intensities: PhaseAmrapRoutineIntensityTable): Map<Phase, RoutineIntensity> =
        routineIntensityMapFactory { phase ->
            val (amrapRoutineBaseRepetitions, amrapRoutineIntensityPercentages) =
                intensities[phase]!!

            RoutineIntensity(
                intensityPercentages = amrapRoutineIntensityPercentages,
                repetitions = amrapRoutineBaseRepetitions
            )
        }

    private inline fun <T> routineIntensityMapFactory(create: (Phase) -> T): Map<Phase, T> {
        return mutableMapOf<Phase, T>().apply {
            Phase.values().forEach { phase ->
                val result = create(phase)
                put(phase, result)
            }
        }.toMap()
    }
}