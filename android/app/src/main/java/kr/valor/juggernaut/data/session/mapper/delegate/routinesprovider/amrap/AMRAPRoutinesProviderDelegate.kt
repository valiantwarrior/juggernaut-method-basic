package kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.amrap

import kr.valor.juggernaut.common.Phase
import kr.valor.juggernaut.common.createPhaseBasedKeyMapAndReturn
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutineIntensity
import kr.valor.juggernaut.data.session.mapper.delegate.routinesprovider.RoutinesProviderDelegate
import kr.valor.juggernaut.domain.session.model.Routine
import kr.valor.juggernaut.domain.session.model.AmrapSession.AmrapSessionRoutine as AmrapSessionRoutine

abstract class AMRAPRoutinesProviderDelegate: RoutinesProviderDelegate {

    protected abstract val warmupRoutinesIntensities: Map<Phase, List<RoutineIntensity>>

    protected abstract val amrapRoutineIntensity: Map<Phase, RoutineIntensity>

    protected abstract val routinesPropertyMediateAction: (Double) -> Double

    final override fun provideRoutines(phase: Phase, tmWeights: Double): AmrapSessionRoutine {
        val warmupRoutineIntensitiesOfCurrentPhase = warmupRoutinesIntensities[phase]!!
        val amrapRoutineIntensityOfCurrentPhase = amrapRoutineIntensity[phase]!!

        val warmupRoutines = mutableListOf<Routine>()
            .apply {
                val totalWarmupRoutinesNumber = warmupRoutineIntensitiesOfCurrentPhase.size

                repeat(totalWarmupRoutinesNumber) { index ->
                    val (repetitions, intensityPercentage) = warmupRoutineIntensitiesOfCurrentPhase[index]
                    val routineWeightsByIntensity = routinesPropertyMediateAction(tmWeights * intensityPercentage)
                    val warmupRoutine = Routine(
                        weights = routineWeightsByIntensity,
                        reps = repetitions
                    )
                    add(warmupRoutine)
                }
            }
            .toList()

        val amrapRoutine = with(amrapRoutineIntensityOfCurrentPhase) {
            val (repetitions, intensityPercentage) = this

            Routine(
                weights = routinesPropertyMediateAction(tmWeights * intensityPercentage),
                reps = repetitions
            )
        }

        return AmrapSessionRoutine(
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
                            repetitions = repetitions,
                            intensityPercentage = intensity
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
                repetitions = amrapRoutineBaseRepetitions,
                intensityPercentage = amrapRoutineIntensityPercentages
            )
        }

    private inline fun <T> routineIntensityMapFactory(create: (Phase) -> T): Map<Phase, T> =
        createPhaseBasedKeyMapAndReturn(create)
}